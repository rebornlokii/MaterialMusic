package com.material.music.utils

<<<<<<< HEAD
import android.content.res.Resources
import androidx.annotation.PluralsRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalResources
import com.material.music.R
=======
>>>>>>> origin/main
import com.material.music.data.model.Song
import java.util.Locale
import java.util.concurrent.TimeUnit

fun formatDuration(milliseconds: Long): String {
    if (milliseconds <= 0L) return "00:00"

    val totalSeconds = milliseconds / 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return if (hours > 0) {
        String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format(Locale.US, "%02d:%02d", minutes, seconds)
    }
}

<<<<<<< HEAD
// The helpers below take the caller's Resources so they follow the in-app language. Never pass
// the Application's: it is locale-wrapped once at process start and goes stale after a switch.
// Composables use the overloads that read LocalResources.

fun formatTotalDuration(resources: Resources, songs: List<Song>): String {
=======
fun formatTotalDuration(songs: List<Song>): String {
>>>>>>> origin/main
    val totalMillis = songs.sumOf { it.duration }
    val hours = TimeUnit.MILLISECONDS.toHours(totalMillis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(totalMillis) % 60
    return if (hours > 0) {
<<<<<<< HEAD
        resources.getString(R.string.duration_hours_minutes_long, hours, minutes)
    } else {
        resources.getString(R.string.duration_minutes_long, minutes)
    }
}

@Composable
@ReadOnlyComposable
fun formatTotalDuration(songs: List<Song>): String =
    formatTotalDuration(LocalResources.current, songs)

fun formatListeningDurationLong(resources: Resources, milliseconds: Long): String {
=======
        String.format(Locale.US, "%d h %02d min", hours, minutes)
    } else {
        String.format(Locale.US, "%d min", minutes)
    }
}

fun formatListeningDurationLong(milliseconds: Long): String {
>>>>>>> origin/main
    val totalMinutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60
    return when {
<<<<<<< HEAD
        hours > 0 && minutes > 0 -> resources.getString(R.string.duration_hours_minutes_medium, hours, minutes)
        hours > 0 -> resources.getString(R.string.duration_hours_medium, hours)
        minutes > 0 -> resources.getString(R.string.duration_minutes_medium, minutes)
        else -> resources.getString(R.string.duration_seconds_medium, seconds)
    }
}

@Composable
@ReadOnlyComposable
fun formatListeningDurationLong(milliseconds: Long): String =
    formatListeningDurationLong(LocalResources.current, milliseconds)

fun formatListeningDurationCompact(resources: Resources, milliseconds: Long): String {
=======
        hours > 0 && minutes > 0 -> String.format(Locale.US, "%d h %02d m", hours, minutes)
        hours > 0 -> String.format(Locale.US, "%d h", hours)
        minutes > 0 -> String.format(Locale.US, "%d m", minutes)
        else -> String.format(Locale.US, "%d s", seconds)
    }
}

fun formatListeningDurationCompact(milliseconds: Long): String {
>>>>>>> origin/main
    val totalMinutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60
    return when {
<<<<<<< HEAD
        hours > 0 && minutes > 0 -> resources.getString(R.string.duration_hours_minutes_short, hours, minutes)
        hours > 0 -> resources.getString(R.string.duration_hours_short, hours)
        minutes > 0 -> resources.getString(R.string.duration_minutes_short, minutes)
        else -> resources.getString(R.string.duration_seconds_short, seconds)
    }
}

@Composable
@ReadOnlyComposable
fun formatListeningDurationCompact(milliseconds: Long): String =
    formatListeningDurationCompact(LocalResources.current, milliseconds)

fun formatSongCount(resources: Resources, count: Int): String =
    resources.getQuantityString(R.plurals.song_count, count, count)

@Composable
@ReadOnlyComposable
fun formatSongCount(count: Int): String = formatSongCount(LocalResources.current, count)

fun formatTimeAgo(
    resources: Resources,
    timestamp: Long,
    nowMs: Long = System.currentTimeMillis()
): String {
    if (timestamp <= 0) return resources.getString(R.string.time_ago_never)
    // A timestamp slightly in the future (clock skew) reads as just now, not a negative count.
    val diff = (nowMs - timestamp).coerceAtLeast(0L)
=======
        hours > 0 && minutes > 0 -> String.format(Locale.US, "%dh %02dm", hours, minutes)
        hours > 0 -> String.format(Locale.US, "%dh", hours)
        minutes > 0 -> String.format(Locale.US, "%dm", minutes)
        else -> String.format(Locale.US, "%ds", seconds)
    }
}

fun formatSongCount(count: Int): String {
    return if (count <= 1) "$count Song" else "$count Songs"
}

fun formatTimeAgo(timestamp: Long): String {
    if (timestamp <= 0) return "Never"
    val diff = System.currentTimeMillis() - timestamp
>>>>>>> origin/main
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
<<<<<<< HEAD
        days > 0 -> resources.countString(R.plurals.time_ago_days, days)
        hours > 0 -> resources.countString(R.plurals.time_ago_hours, hours)
        minutes > 0 -> resources.countString(R.plurals.time_ago_minutes, minutes)
        else -> resources.getString(R.string.time_ago_just_now)
    }
}

@Composable
@ReadOnlyComposable
fun formatTimeAgo(timestamp: Long): String = formatTimeAgo(LocalResources.current, timestamp)

private fun Resources.countString(@PluralsRes id: Int, count: Long): String {
    val quantity = count.coerceAtMost(Int.MAX_VALUE.toLong()).toInt()
    return getQuantityString(id, quantity, quantity)
}
=======
        days > 0 -> if (days == 1L) "1 day ago" else "$days days ago"
        hours > 0 -> if (hours == 1L) "1 hour ago" else "$hours hours ago"
        minutes > 0 -> if (minutes == 1L) "1 minute ago" else "$minutes minutes ago"
        else -> "Just now"
    }
}
>>>>>>> origin/main
