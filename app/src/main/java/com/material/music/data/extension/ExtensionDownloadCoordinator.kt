package com.material.music.data.extension

import com.material.music.data.offline.CloudOfflineRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Sends an extension result into the app's existing download queue.
 *
 * There is one queue (`offline_tracks`) and one Downloads screen; extension
 * downloads are the same queue with a `mornye://` source, so progress, retry
 * and the completed list all come for free.
 */
@Singleton
class ExtensionDownloadCoordinator @Inject constructor(
    private val repository: CloudOfflineRepository,
) {

    suspend fun enqueue(track: ExtensionTrack, provider: String): Boolean {
        val song = track.toSong(provider)
        return repository.enqueueAll(listOf(song)) > 0
    }

    suspend fun enqueueAll(tracks: List<ExtensionTrack>, provider: String): Int =
        repository.enqueueAll(tracks.map { it.toSong(provider) })

    suspend fun retry(sourceUri: String) = repository.retry(sourceUri)

    suspend fun remove(sourceUri: String) = repository.remove(sourceUri)
}