package com.material.music.data.extension

import com.google.gson.annotations.SerializedName

/**
 * Wire models for the Mornye extension host.
 *
 * Field names mirror the JSON produced by the Rust core (see
 * `rust_backend/crates/extensions/src/provider.js`), which is snake_case.
 * Keep these in sync with the upstream normalizer rather than "tidying" names.
 */

data class ExtensionTrack(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("artists") val artists: String = "",
    @SerializedName("album_name") val albumName: String = "",
    @SerializedName("album_artist") val albumArtist: String? = null,
    @SerializedName("album_id") val albumId: String? = null,
    @SerializedName("artist_id") val artistId: String? = null,
    @SerializedName("duration_ms") val durationMs: Long = 0L,
    @SerializedName("cover_url") val coverUrl: String? = null,
    @SerializedName("preview_url") val previewUrl: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("track_number") val trackNumber: Int? = null,
    @SerializedName("total_tracks") val totalTracks: Int? = null,
    @SerializedName("disc_number") val discNumber: Int? = null,
    @SerializedName("total_discs") val totalDiscs: Int? = null,
    @SerializedName("isrc") val isrc: String? = null,
    @SerializedName("provider_id") val providerId: String = "",
    @SerializedName("item_type") val itemType: String? = null,
    @SerializedName("album_type") val albumType: String? = null,
    @SerializedName("explicit") val explicit: Boolean = false,
    @SerializedName("upc") val upc: String? = null,
    @SerializedName("audio_quality") val audioQuality: String? = null,
    @SerializedName("audio_modes") val audioModes: String? = null,
    @SerializedName("spotify_id") val spotifyId: String? = null,
    @SerializedName("deezer_id") val deezerId: String? = null,
    @SerializedName("tidal_id") val tidalId: String? = null,
    @SerializedName("qobuz_id") val qobuzId: String? = null,
    @SerializedName("genre") val genre: String? = null,
    @SerializedName("label") val label: String? = null,
    @SerializedName("copyright") val copyright: String? = null,
    @SerializedName("composer") val composer: String? = null,
    @SerializedName("comment") val comment: String? = null,
) {
    /** A search result that is really an album/artist/playlist card. */
    val isCollection: Boolean
        get() = itemType == "album" || itemType == "playlist" || itemType == "artist"
}

data class ExtensionAlbum(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("artists") val artists: String = "",
    @SerializedName("artist_id") val artistId: String? = null,
    @SerializedName("cover_url") val coverUrl: String? = null,
    @SerializedName("header_image") val headerImage: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("total_tracks") val totalTracks: Int = 0,
    @SerializedName("album_type") val albumType: String? = null,
    @SerializedName("provider_id") val providerId: String = "",
    @SerializedName("description") val description: String? = null,
    @SerializedName("tracks") val tracks: List<ExtensionTrack> = emptyList(),
)

data class ExtensionArtist(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("image_url") val imageUrl: String? = null,
    @SerializedName("header_image") val headerImage: String? = null,
    @SerializedName("header_video") val headerVideo: String? = null,
    @SerializedName("provider_id") val providerId: String = "",
    @SerializedName("albums") val albums: List<ExtensionAlbum> = emptyList(),
    @SerializedName("top_tracks") val topTracks: List<ExtensionTrack> = emptyList(),
)

/** One card in a provider home feed. */
data class ExtensionHomeItem(
    @SerializedName("id") val id: String = "",
    @SerializedName("uri") val uri: String = "",
    @SerializedName("type") val type: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("artists") val artists: String = "",
    @SerializedName("description") val description: String? = null,
    @SerializedName("cover_url") val coverUrl: String? = null,
    @SerializedName("featured_cover_url") val featuredCoverUrl: String? = null,
    @SerializedName("heading") val heading: String? = null,
    @SerializedName("provider_id") val providerId: String = "",
    @SerializedName("album_id") val albumId: String? = null,
    @SerializedName("album_name") val albumName: String? = null,
    @SerializedName("duration_ms") val durationMs: Long = 0L,
)

data class ExtensionHomeSection(
    @SerializedName("uri") val uri: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("layout") val layout: String? = null,
    @SerializedName("items") val items: List<ExtensionHomeItem> = emptyList(),
)

data class ExtensionHomeFeed(
    @SerializedName("success") val success: Boolean = false,
    @SerializedName("greeting") val greeting: String? = null,
    @SerializedName("sections") val sections: List<ExtensionHomeSection> = emptyList(),
)

/** Result of `download(trackId, quality, outputPath, …)`. */
data class ExtensionDownloadResult(
    @SerializedName("success") val success: Boolean = false,
    @SerializedName("file_path") val filePath: String? = null,
    @SerializedName("already_exists") val alreadyExists: Boolean = false,
    @SerializedName("bit_depth") val bitDepth: Int? = null,
    @SerializedName("sample_rate") val sampleRate: Int? = null,
    @SerializedName("audio_codec") val audioCodec: String? = null,
    @SerializedName("duration_ms") val durationMs: Long? = null,
    @SerializedName("error_message") val errorMessage: String? = null,
    @SerializedName("error_type") val errorType: String? = null,
    @SerializedName("retry_after_seconds") val retryAfterSeconds: Int? = null,
    @SerializedName("isrc") val isrc: String? = null,
    @SerializedName("cover_url") val coverUrl: String? = null,
    @SerializedName("lyrics_lrc") val lyricsLrc: String? = null,
    @SerializedName("actual_extension") val actualExtension: String? = null,
    @SerializedName("output_extension") val outputExtension: String? = null,
    @SerializedName("actual_container") val actualContainer: String? = null,
    @SerializedName("requires_container_conversion") val requiresContainerConversion: Boolean = false,
)

data class ExtensionLyricsLine(
    @SerializedName("startTimeMs") val startTimeMs: Long = 0L,
    @SerializedName("words") val words: String = "",
    @SerializedName("endTimeMs") val endTimeMs: Long? = null,
)

data class ExtensionLyrics(
    @SerializedName("syncType") val syncType: String? = null,
    @SerializedName("instrumental") val instrumental: Boolean = false,
    @SerializedName("plainLyrics") val plainLyrics: String = "",
    @SerializedName("provider") val provider: String = "",
    @SerializedName("lines") val lines: List<ExtensionLyricsLine>? = null,
) {
    val isSynced: Boolean get() = syncType == "LINE_SYNCED" && !lines.isNullOrEmpty()
}

/** Cached lyrics plus the source label the core reports ("Extension: tidal-web", …). */
data class ExtensionLyricsWithSource(
    @SerializedName("sync_type") val syncType: String? = null,
    @SerializedName("instrumental") val instrumental: Boolean = false,
    @SerializedName("plain_lyrics") val plainLyrics: String = "",
    @SerializedName("provider") val provider: String = "",
    @SerializedName("source") val source: String? = null,
    @SerializedName("lines") val lines: List<ExtensionLyricsLine>? = null,
)