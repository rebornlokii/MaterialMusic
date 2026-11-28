package com.material.music.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.material.music.data.database.MusicDao
import com.material.music.data.database.OfflineTrackDao
import com.material.music.data.extension.ExtensionDownloadSettings
import com.material.music.data.extension.ExtensionFileName
import com.material.music.data.extension.ExtensionHost
import com.material.music.data.extension.ExtensionLibrary
import com.material.music.data.extension.ExtensionSongUri
import com.material.music.data.extension.ExtensionVerificationNotifier
import com.material.music.data.extension.SafFolder
import com.material.music.data.offline.OfflineDownloadStatus
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.util.Locale

/**
 * Downloads one extension track into the user-visible folder (or app storage).
 *
 * The engine does the work — availability check, transfer, container probe and
 * metadata/lyrics/cover embedding — so this worker only builds the request,
 * publishes the finished file and records state.
 */
@HiltWorker
class ExtensionDownloadWorker @AssistedInject constructor(
    @Assisted applicationContext: Context,
    @Assisted params: WorkerParameters,
    private val host: ExtensionHost,
    private val dao: OfflineTrackDao,
    private val musicDao: MusicDao,
    private val settings: ExtensionDownloadSettings,
    private val gson: Gson,
) : CoroutineWorker(applicationContext, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val downloadId = inputData.getString(KEY_DOWNLOAD_ID) ?: return@withContext Result.failure()
        val attemptId = inputData.getString(KEY_ATTEMPT_ID) ?: return@withContext Result.failure()
        val sourceUri = inputData.getString(KEY_SOURCE_URI) ?: return@withContext Result.failure()

        val entity = dao.getByDownloadId(downloadId) ?: return@withContext Result.failure()
        if (entity.attemptId != attemptId || entity.sourceUri != sourceUri) return@withContext Result.failure()

        val target = ExtensionSongUri.parse(sourceUri) ?: return@withContext Result.failure()
        val (provider, trackId) = target
        val prefs = settings.snapshot()

        gate.withPermit {
            runCatching {
                val staging = File(File(applicationContext.cacheDir, "extension_downloads").apply { mkdirs() }, "$downloadId.$attemptId")
                staging.deleteRecursively()
                staging.mkdirs()
                val startedAt = System.currentTimeMillis()

                dao.updateState(
                    downloadId, attemptId, OfflineDownloadStatus.DOWNLOADING.storageValue,
                    0L, null, null, null, System.currentTimeMillis(),
                )

                // Metadata first: the file name template needs it, and the
                // library row reuses the same result.
                val metadata = runCatching { host.track(provider, trackId) }.getOrNull()

                val result = host.download(provider, request(provider, trackId, staging, prefs).toString())
                if (!result.success) {
                    val message = result.errorMessage ?: result.errorType ?: "Download failed"
                    if (result.errorType?.contains("verification", ignoreCase = true) == true) {
                        ExtensionVerificationNotifier.notify(applicationContext, provider, message)
                    }
                    dao.updateState(
                        downloadId, attemptId, OfflineDownloadStatus.FAILED.storageValue,
                        0L, null, null, message, System.currentTimeMillis(),
                    )
                    return@withPermit Result.failure()
                }

                val produced = newestFile(staging, startedAt)
                    ?: error("Engine reported success but produced no file")
                val extension = produced.extension
                val name = SafFolder.displayName(
                    ExtensionFileName.expand(prefs.template, metadata, entity.title, prefs.quality),
                    extension,
                )

                val localPath = publish(produced, name, prefs.treeUri)
                    ?: error("Could not publish the downloaded file")

                dao.updateState(
                    downloadId, attemptId, OfflineDownloadStatus.COMPLETE.storageValue,
                    produced.length(), produced.length(), localPath, null, System.currentTimeMillis(),
                )

                // Make the file part of the library so it is playable offline.
                runCatching {
                    ExtensionLibrary.recordDownloaded(musicDao, entity, metadata, provider, localPath)
                }.onFailure { Timber.w(it, "Could not add %s to the library", sourceUri) }
                staging.deleteRecursively()
                Result.success()
            }.getOrElse {
                Timber.w(it, "Extension download failed for %s", sourceUri)
                dao.updateState(
                    downloadId, attemptId, OfflineDownloadStatus.FAILED.storageValue,
                    0L, null, null, it.message ?: "Download failed", System.currentTimeMillis(),
                )
                if (runAttemptCount < 3) Result.retry() else Result.failure()
            }
        }
    }

    /** Moves the file into the picked folder, or keeps it in app storage. */
    private fun publish(file: File, displayName: String, treeUri: String?): String? {
        if (treeUri.isNullOrBlank()) {
            val target = File(File(applicationContext.filesDir, "downloads").apply { mkdirs() }, displayName)
            file.copyTo(target, overwrite = true)
            return target.absolutePath
        }
        val document = SafFolder.createFile(applicationContext, treeUri, "audio/*", displayName) ?: return null
        if (!SafFolder.copy(applicationContext, file, document)) return null
        return document.toString()
    }

    private fun newestFile(dir: File, since: Long): File? =
        dir.listFiles()?.filter { it.isFile && it.lastModified() >= since }
            ?.maxByOrNull(File::lastModified)

    private fun request(
        provider: String,
        trackId: String,
        staging: File,
        prefs: com.material.music.data.extension.ExtensionDownloadPrefs,
    ): JsonObject = JsonObject().apply {
        addProperty("item_id", "${provider}_$trackId")
        addProperty("service", provider)
        addProperty("download_provider", provider)
        addProperty("provider_track_id", trackId)
        addProperty("quality", prefs.quality.lowercase(Locale.ROOT))
        addProperty("output_dir", staging.absolutePath)
        addProperty("filename_format", prefs.template)
        addProperty("storage_mode", "app")
        addProperty("embed_metadata", true)
        addProperty("embed_lyrics", prefs.embedLyrics)
        addProperty("lyrics_mode", if (prefs.embedLyrics) "embed" else "external")
    }

    companion object {
        const val KEY_DOWNLOAD_ID = "download_id"
        const val KEY_ATTEMPT_ID = "attempt_id"
        const val KEY_SOURCE_URI = "source_uri"
        const val TAG = "extension_download"

        /** The engine is happiest with a small, fixed amount of parallelism. */
        private val gate = Semaphore(3)
    }
}