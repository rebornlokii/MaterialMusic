package com.material.music.presentation.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.material.music.data.extension.ExtensionDownloadCoordinator
import com.material.music.data.extension.ExtensionTrack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Thin bridge so Compose screens can enqueue without knowing about the queue. */
@HiltViewModel
class ExtensionDownloadViewModel @Inject constructor(
    private val coordinator: ExtensionDownloadCoordinator,
) : ViewModel() {

    fun enqueue(track: ExtensionTrack, provider: String) {
        viewModelScope.launch { coordinator.enqueue(track, provider) }
    }
}