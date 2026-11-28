package com.material.music.presentation.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.material.music.data.extension.ExtensionHost
import com.material.music.data.extension.ExtensionTrack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ExtensionSearchState {
    data object Idle : ExtensionSearchState
    data object Loading : ExtensionSearchState
    data class Empty(val query: String) : ExtensionSearchState
    data class Failed(val message: String) : ExtensionSearchState
    data class Results(val query: String, val tracks: List<ExtensionTrack>) : ExtensionSearchState
}

@HiltViewModel
class ExtensionSearchViewModel @Inject constructor(
    private val host: ExtensionHost,
) : ViewModel() {

    private val _state = MutableStateFlow<ExtensionSearchState>(ExtensionSearchState.Idle)
    val state: StateFlow<ExtensionSearchState> = _state.asStateFlow()

    private var job: Job? = null

    fun search(query: String) {
        job?.cancel()
        val trimmed = query.trim()
        if (trimmed.length < 2) {
            _state.value = ExtensionSearchState.Idle
            return
        }
        _state.value = ExtensionSearchState.Loading
        job = viewModelScope.launch {
            runCatching { host.search(trimmed) }
                .onSuccess { results ->
                    _state.value = if (results.isEmpty()) {
                        ExtensionSearchState.Empty(trimmed)
                    } else {
                        ExtensionSearchState.Results(trimmed, results)
                    }
                }
                .onFailure { _state.value = ExtensionSearchState.Failed(it.message ?: "Search failed") }
        }
    }

    fun clear() {
        job?.cancel()
        _state.value = ExtensionSearchState.Idle
    }
}