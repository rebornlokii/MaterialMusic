package com.material.music.data.extension

import com.material.music.data.model.Song

/**
 * Maps an extension result onto the app's unified [Song].
 *
 * The id is synthetic (`mornye_<provider>_<trackId>`) because the local library
 * owns the `songs.id` namespace; the canonical identity is [Song.contentUriString].
 */
fun ExtensionTrack.toSong(providerId: String): Song = Song(
    id = "mornye_${providerId}_$id",
    title = name.ifBlank { id },
    artist = artists,
    artistId = 0L,
    album = albumName,
    albumId = 0L,
    albumArtist = albumArtist,
    path = "",
    contentUriString = ExtensionSongUri.build(providerId, id),
    albumArtUriString = coverUrl,
    duration = if (durationMs > 0) durationMs / 1000 else 0L,
    genre = genre,
    lyrics = null,
    isFavorite = false,
    trackNumber = trackNumber ?: 0,
    discNumber = discNumber,
    year = releaseDate?.take(4)?.toIntOrNull() ?: 0,
    dateAdded = 0L,
    dateModified = 0L,
    mimeType = null,
    bitrate = null,
    sampleRate = null,
)

/** The provider a result came from, falling back to the id embedded in its URI. */
fun ExtensionTrack.providerOr(uriProvider: String?): String =
    providerId.ifBlank { uriProvider.orEmpty() }