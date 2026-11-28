package com.material.music.presentation.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.material.music.data.extension.ExtensionAlbum
import com.material.music.data.extension.ExtensionHost
import com.material.music.data.extension.ExtensionTrack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ExtensionAlbumState {
    data object Loading : ExtensionAlbumState
    data class Failed(val message: String) : ExtensionAlbumState
    data class Loaded(
        val album: ExtensionAlbum,
        val tracks: List<ExtensionTrack>,
    ) : ExtensionAlbumState
}

@HiltViewModel
class ExtensionAlbumViewModel @Inject constructor(
    private val host: ExtensionHost,
) : ViewModel() {

    private val _state = MutableStateFlow<ExtensionAlbumState>(ExtensionAlbumState.Loading)
    val state: StateFlow<ExtensionAlbumState> = _state.asStateFlow()

    fun load(provider: String, albumId: String) {
        _state.value = ExtensionAlbumState.Loading
        viewModelScope.launch {
            runCatching { host.album(provider, albumId) }
                .onSuccess { album ->
                    val tracks = album.tracks.ifEmpty {
                        // Some providers only fill `tracks` on the album call; if it is
                        // empty the album is still worth showing.
                        emptyList()
                    }
                    _state.value = ExtensionAlbumState.Loaded(album, tracks)
                }
                .onFailure {
                    _state.value = ExtensionAlbumState.Failed(it.message ?: "Could not load album")
                }
        }
    }
}