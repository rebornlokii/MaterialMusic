package com.material.music.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.material.music.BuildConfig
import com.material.music.data.extension.ExtensionBootstrap
import com.material.music.data.extension.ExtensionHost
import com.material.music.data.extension.ExtensionRegistry
import com.material.music.data.extension.isNewerVersion
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import java.security.MessageDigest

/**
 * Installs bundled extensions on first run, then updates them from the store.
 *
 * Updates are opt-out by design: a fresh install has working providers without
 * a network call, and the periodic check only ever replaces a package with a
 * newer version whose checksum matches the registry.
 */
@HiltWorker
class ExtensionUpdateWorker @AssistedInject constructor(
    @Assisted applicationContext: Context,
    @Assisted params: WorkerParameters,
    private val host: ExtensionHost,
    private val bootstrap: ExtensionBootstrap,
    private val okHttpClient: OkHttpClient,
    private val gson: Gson,
) : CoroutineWorker(applicationContext, params) {

    override suspend fun doWork(): Result = runCatching {
        bootstrap.installBundled()
        updateFromRegistry()
        Result.success()
    }.getOrElse {
        Timber.w(it, "Extension update run failed")
        if (runAttemptCount < 3) Result.retry() else Result.success()
    }

    private suspend fun updateFromRegistry() = withContext(Dispatchers.IO) {
        val registry = download(ExtensionRegistry.DEFAULT_URL) ?: return@withContext
        val entries = ExtensionRegistry.parse(registry, gson)
        if (entries.isEmpty()) return@withContext

        var updated = 0
        for (entry in entries) {
            val current = bootstrap.installedVersion(entry.id) ?: continue
            if (!isNewerVersion(entry.version, current)) continue

            val min = entry.minAppVersion
            if (min != null && isNewerVersion(min, BuildConfig.VERSION_NAME)) continue

            val file = download(entry.downloadUrl) ?: continue
            if (!checksumMatches(file, entry.sha256)) {
                Timber.w("Skipping %s: checksum mismatch", entry.id)
                continue
            }
            runCatching {
                host.install(file.absolutePath)
                host.setEnabled(entry.id, true)
                bootstrap.markInstalled(entry.id, entry.version)
                updated++
            }.onFailure { Timber.w(it, "Could not update %s", entry.id) }
        }
        if (updated > 0) {
            runCatching { host.loadAll() }
            Timber.i("Updated %d extensions", updated)
        }
    }

    private fun download(url: String): String? = runCatching {
        val request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return null
            val body = response.body?.byteString() ?: return null
            val file = java.io.File.createTempFile("mornye-ext", ".sflx", applicationContext.cacheDir)
            file.writeBytes(body.toByteArray())
            file.absolutePath
        }
    }.getOrNull()

    private fun checksumMatches(path: String, expected: String?): Boolean {
        if (expected.isNullOrBlank()) return true
        val digest = MessageDigest.getInstance("SHA-256")
        java.io.File(path).inputStream().use { input ->
            val buffer = ByteArray(64 * 1024)
            while (true) {
                val read = input.read(buffer)
                if (read <= 0) break
                digest.update(buffer, 0, read)
            }
        }
        val actual = digest.digest().joinToString("") { "%02x".format(it) }
        return actual.equals(expected.removePrefix("sha256:"), ignoreCase = true)
    }
}