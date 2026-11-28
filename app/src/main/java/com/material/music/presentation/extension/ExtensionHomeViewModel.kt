package com.material.music.presentation.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.material.music.data.extension.ExtensionHomeFeed
import com.material.music.data.extension.ExtensionHost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ExtensionHomeState {
    data object Loading : ExtensionHomeState
    data object Empty : ExtensionHomeState
    data class Ready(val feed: ExtensionHomeFeed) : ExtensionHomeState
}

/**
 * Home shelves from whichever installed provider answers first.
 *
 * The provider is a preference; the fallback list keeps the shelf useful when
 * the preferred one has no home feed (a metadata-only extension, say).
 */
@HiltViewModel
class ExtensionHomeViewModel @Inject constructor(
    private val host: ExtensionHost,
) : ViewModel() {

    private val _state = MutableStateFlow<ExtensionHomeState>(ExtensionHomeState.Loading)
    val state: StateFlow<ExtensionHomeState> = _state.asStateFlow()

    private val candidates = listOf(
        "spotify-web",
        "jellyfin",
        "deezer",
        "ytmusic-spotiflac",
        "soundcloud",
        "tidal-web",
    )

    init { refresh() }

    fun refresh() {
        _state.value = ExtensionHomeState.Loading
        viewModelScope.launch {
            for (provider in candidates) {
                val feed = runCatching { host.homeFeed(provider) }.getOrNull() ?: continue
                if (feed.success && feed.sections.any { it.items.isNotEmpty() }) {
                    _state.value = ExtensionHomeState.Ready(feed)
                    return@launch
                }
            }
            _state.value = ExtensionHomeState.Empty
        }
    }
}