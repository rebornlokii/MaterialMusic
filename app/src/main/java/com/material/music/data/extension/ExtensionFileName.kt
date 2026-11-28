package com.material.music.data.extension

import java.util.Locale

/**
 * Expands the download name template into a display name.
 *
 * Supported tokens: `{artist}`, `{album}`, `{track}`, `{title}`, `{quality}`.
 * Unknown tokens are left as-is, and the result is sanitised for SAF display
 * names (no path separators, control characters or reserved names).
 */
object ExtensionFileName {

    fun expand(template: String, track: ExtensionTrack?, fallbackTitle: String, quality: String): String {
        val artist = track?.artists?.takeIf { it.isNotBlank() } ?: "Unknown artist"
        val album = track?.albumName?.takeIf { it.isNotBlank() } ?: "Singles"
        val title = track?.name?.takeIf { it.isNotBlank() } ?: fallbackTitle
        val number = track?.trackNumber?.takeIf { it > 0 }?.toString() ?: "00"

        val expanded = template
            .replace("{artist}", artist)
            .replace("{album}", album)
            .replace("{track}", number)
            .replace("{title}", title)
            .replace("{quality}", quality.lowercase(Locale.ROOT))

        // A template may contain folders; keep only the last segment for a
        // document display name.
        val leaf = expanded.substringAfterLast('/').substringAfterLast('\\')
        return sanitize(leaf.ifBlank { title })
    }

    private fun sanitize(raw: String): String {
        val cleaned = raw.replace(Regex("""[\\/:*?"<>|\r\n\t]"""), "_").trim()
        val name = cleaned.ifBlank { "download" }.take(120)
        return if (RESERVED.any { it.equals(name, ignoreCase = true) }) "_$name" else name
    }

    private val RESERVED = listOf(
        "CON", "PRN", "AUX", "NUL",
        "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9",
        "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9",
    )
}