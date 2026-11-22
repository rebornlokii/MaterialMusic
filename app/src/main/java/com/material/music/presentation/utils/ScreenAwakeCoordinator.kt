package com.material.music.presentation.utils

import android.view.View
import androidx.annotation.MainThread

/**
 * Coordinates screen-awake (keepScreenOn) requests across multiple composable views
 * (e.g., [com.material.music.presentation.components.LyricsSheet] and
 * [com.material.music.presentation.components.player.EmbeddedLyricsPane])
 * so that one view closing does not inadvertently clear another active view's screen-awake flag.
 */
@MainThread
object ScreenAwakeCoordinator {
    private val activeRequesters = mutableSetOf<String>()

    fun request(requester: String, keepOn: Boolean, view: View? = null) {
        if (keepOn) {
            activeRequesters.add(requester)
        } else {
            activeRequesters.remove(requester)
        }
        view?.keepScreenOn = activeRequesters.isNotEmpty()
    }

    fun reset(view: View? = null) {
        activeRequesters.clear()
        view?.keepScreenOn = false
    }

    fun isScreenAwakeRequested(): Boolean = activeRequesters.isNotEmpty()
}
