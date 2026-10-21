package com.material.music.presentation.viewmodel

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.material.music.data.preferences.AlbumArtColorAccuracy
import com.material.music.data.preferences.AlbumArtPaletteStyle
import com.material.music.data.preferences.ThemePreference
import com.material.music.data.preferences.ThemePreferencesRepository
import com.material.music.utils.LocalArtworkUri
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ThemeStateHolderArtworkTest {
    private val processor = mockk<ColorSchemeProcessor>()
    private val oldScheme = scheme(Color.Red)

    @Test
    fun `stale owner clearing keeps album palettes generating until the current owner clears`() = runTest {
        coEvery { processor.getOrGenerateColorScheme(any(), any(), any(), any()) } returns oldScheme

        val holder = holder()
        val staleOwner = childScope()
        val currentOwner = childScope()
        holder.initialize(staleOwner)
        holder.initialize(currentOwner)
        runCurrent()
        val boundUri = LocalArtworkUri.buildSongUri(1L)
        val unboundUri = LocalArtworkUri.buildSongUri(2L)

        holder.onCleared(staleOwner)
        holder.ensureAlbumColorScheme(boundUri)
        holder.getAlbumColorSchemeFlow(boundUri, eager = false).first { it == oldScheme }

        holder.onCleared(currentOwner)
        holder.ensureAlbumColorScheme(unboundUri)

        assertNull(holder.getAlbumColorSchemeFlow(unboundUri, eager = false).value)
        coVerify(exactly = 1) { processor.getOrGenerateColorScheme(boundUri, any(), any(), any()) }
        coVerify(exactly = 0) { processor.getOrGenerateColorScheme(unboundUri, any(), any(), any()) }
        staleOwner.cancel()
        currentOwner.cancel()
    }

    private fun TestScope.childScope(): CoroutineScope =
        CoroutineScope(backgroundScope.coroutineContext + Job(backgroundScope.coroutineContext[Job]))

    private fun holder(): ThemeStateHolder {
        val preferences = mockk<ThemePreferencesRepository> {
            every { playerThemePreferenceFlow } returns flowOf(ThemePreference.ALBUM_ART)
            every { albumArtPaletteStyleFlow } returns flowOf(AlbumArtPaletteStyle.default)
            every { albumArtColorAccuracyFlow } returns flowOf(AlbumArtColorAccuracy.DEFAULT)
        }
        return ThemeStateHolder(processor, preferences)
    }

    private fun scheme(color: Color): ColorSchemePair = ColorSchemePair(
        light = lightColorScheme(primary = color),
        dark = darkColorScheme(primary = color)
    )
}
