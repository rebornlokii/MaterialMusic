package com.material.music.data.extension

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.mornye.backend.ExtensionManager
import com.mornye.backend.LyricsRequest
import com.mornye.backend.RequestLease
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Kotlin facade over the Mornye Rust core (`libmornye_mobile.so`).
 *
 * The core owns the extension runtime, the provider chain, the lyrics providers,
 * metadata enrichment and the tag writers. This class only marshals calls and
 * parses JSON; it deliberately holds no policy of its own.
 *
 * Every call blocks the native thread, so callers must be on [Dispatchers.IO]
 * (enforced here) and the manager must be created once per process.
 */
class ExtensionHost(
    private val sourceDirectory: String,
    private val dataDirectory: String,
    private val masterKey: String,
    private val appVersion: String,
    private val providersJson: String,
    private val optionsJson: String,
    private val timeoutMs: ULong = 30_000u,
) : AutoCloseable {

    private val gson = Gson()

    @Volatile
    private var manager: ExtensionManager? = null

    /** The native manager, created on first use. */
    private fun native(): ExtensionManager {
        manager?.let { return it }
        return synchronized(this) {
            manager ?: ExtensionManager.withLyricsSettings(
                sourceDirectory,
                dataDirectory,
                masterKey,
                appVersion,
                timeoutMs,
                providersJson,
                optionsJson,
            ).also { manager = it }
        }
    }

    private fun environment() = native().environment()

    // ---------------------------------------------------------------- extensions

    /** Installs a `.sflx` package. Returns the parsed manifest on success. */
    suspend fun install(packagePath: String): JsonObject = withContext(Dispatchers.IO) {
        parseObject(native().install(packagePath), "install")
    }

    suspend fun installed(): JsonObject = withContext(Dispatchers.IO) {
        parseObject(native().installed(), "installed")
    }

    suspend fun loadAll(): JsonObject = withContext(Dispatchers.IO) {
        parseObject(native().loadAll(), "loadAll")
    }

    suspend fun setEnabled(extensionId: String, enabled: Boolean) = withContext(Dispatchers.IO) {
        native().setEnabled(extensionId, enabled)
    }

    /** `kind` is "download" or "metadata". */
    suspend fun setProviderPriority(kind: String, ids: List<String>) = withContext(Dispatchers.IO) {
        native().setProviderPriority(kind, ids)
    }

    suspend fun setFallbackProviders(ids: List<String>?) = withContext(Dispatchers.IO) {
        native().setFallbackProviders(ids)
    }

    // ------------------------------------------------------------------ metadata

    suspend fun search(
        query: String,
        limit: Int = 20,
        itemId: String = "",
    ): List<ExtensionTrack> = withContext(Dispatchers.IO) {
        val raw = native().searchMetadataProviders(query, limit.toLong(), true, itemId, timeoutMs)
        parseArray<ExtensionTrack>(raw, "search")
    }

    suspend fun customSearch(
        extensionId: String,
        query: String,
        filter: String? = null,
        limit: Int = 20,
    ): List<ExtensionTrack> = withContext(Dispatchers.IO) {
        val options = JsonObject().apply {
            addProperty("limit", limit)
            if (filter != null) addProperty("filter", filter)
        }
        parseArray(native().customSearchJson(extensionId, query, options.toString(), null), "customSearch")
    }

    suspend fun album(providerId: String, albumId: String): ExtensionAlbum = withContext(Dispatchers.IO) {
        parse(native().getProviderMetadataJson(providerId, "album", albumId, null), "album")
    }

    suspend fun artist(providerId: String, artistId: String): ExtensionArtist = withContext(Dispatchers.IO) {
        parse(native().getProviderMetadataJson(providerId, "artist", artistId, null), "artist")
    }

    suspend fun playlist(providerId: String, playlistId: String): ExtensionAlbum = withContext(Dispatchers.IO) {
        parse(native().getProviderMetadataJson(providerId, "playlist", playlistId, null), "playlist")
    }

    /** Resolves a shared URL (spotify:…, open.spotify.com/…) to a track/album/playlist. */
    suspend fun handleUrl(url: String): JsonObject = withContext(Dispatchers.IO) {
        parseObject(native().handleUrlJson(url), "handleUrl")
    }

    // ---------------------------------------------------------------- home feed

    suspend fun homeFeed(extensionId: String): ExtensionHomeFeed = withContext(Dispatchers.IO) {
        parse(native().getExtensionHomeFeedJson(extensionId, null), "homeFeed")
    }

    suspend fun relatedTracks(
        extensionId: String,
        seeds: List<ExtensionTrack>,
        limit: Int = 20,
    ): List<ExtensionTrack> = withContext(Dispatchers.IO) {
        val seedsJson = gson.toJson(seeds)
        val options = JsonObject().apply { addProperty("limit", limit) }
        parseArray(
            native().getExtensionRelatedTracksJson(extensionId, seedsJson, options.toString(), null),
            "relatedTracks",
        )
    }

    // ---------------------------------------------------------------- downloads

    /**
     * Runs one download through the configured provider chain.
     *
     * [requestJson] is the snake_case payload built by [DownloadRequestFactory].
     * The returned result carries `file_path` on success and a typed `error_type`
     * on failure; it never throws for a provider-level failure.
     */
    suspend fun downloadByStrategy(requestJson: String): ExtensionDownloadResult = withContext(Dispatchers.IO) {
        parse(native().downloadByStrategy(requestJson), "downloadByStrategy")
    }

    /** Downloads through one specific extension, bypassing the fallback chain. */
    suspend fun download(
        extensionId: String,
        requestJson: String,
        resolutionTimeoutMs: ULong = 60_000u,
    ): ExtensionDownloadResult = withContext(Dispatchers.IO) {
        parse(native().download(extensionId, requestJson, resolutionTimeoutMs), "download")
    }

    /** Leases are required for cancellation; release in a `finally`. */
    fun acquireLease(itemId: String): RequestLease = environment().downloadState().acquire(itemId)

    fun cancelDownload(itemId: String) {
        environment().downloadState().cancelDownload(itemId)
    }

    // ------------------------------------------------------------------- lyrics

    suspend fun lyrics(
        track: String,
        artist: String,
        durationMs: Long,
        spotifyId: String = "",
        filePath: String = "",
    ): ExtensionLyricsWithSource = withContext(Dispatchers.IO) {
        val request = LyricsRequest(spotifyId, track, artist, filePath, durationMs)
        parse(native().getLyricsLrcWithSource(request, null), "lyrics")
    }

    /** One provider's lyrics, for the "which provider has it" picker. */
    suspend fun lyricsFromProvider(
        provider: String,
        track: String,
        artist: String,
        durationMs: Long,
    ): ExtensionLyrics = withContext(Dispatchers.IO) {
        val request = LyricsRequest("", track, artist, "", durationMs)
        parse(native().getLyricsFromProvider(provider, request, null), "lyricsFromProvider")
    }

    suspend fun setLyricsProviders(providers: List<String>) = withContext(Dispatchers.IO) {
        native().setLyricsProvidersJson(gson.toJson(providers))
    }

    suspend fun setLyricsFetchOptions(optionsJson: String) = withContext(Dispatchers.IO) {
        native().setLyricsFetchOptionsJson(optionsJson)
    }

    // ------------------------------------------------------------------ helpers

    private inline fun <reified T> parse(raw: String, what: String): T {
        val parsed = gson.fromJson<T>(raw, object : TypeToken<T>() {}.type)
            ?: throw ExtensionHostException("$what returned no result")
        return parsed
    }

    private fun parseObject(raw: String, what: String): JsonObject {
        val parsed = gson.fromJson<JsonObject>(raw, JsonObject::class.java)
            ?: throw ExtensionHostException("$what returned no result")
        return parsed
    }

    private inline fun <reified T> parseArray(raw: String, what: String): List<T> {
        val parsed = gson.fromJson<List<T>>(raw, object : TypeToken<List<T>>() {}.type)
        return parsed ?: emptyList()
    }

    override fun close() {
        synchronized(this) {
            manager?.close()
            manager = null
        }
    }

    override fun toString(): String = "ExtensionHost(source=$sourceDirectory)"
}

class ExtensionHostException(message: String) : Exception(message)

private fun ExtensionManager.close() {
    try {
        shutdown()
    } catch (t: Throwable) {
        Timber.w(t, "ExtensionManager.shutdown failed")
    }
}