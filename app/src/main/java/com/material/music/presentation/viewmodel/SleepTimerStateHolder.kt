package com.material.music.presentation.viewmodel

<<<<<<< HEAD
import android.content.Context
=======
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
>>>>>>> origin/main
import android.os.Bundle
import androidx.media3.session.MediaController
import androidx.media3.session.SessionCommand
import com.material.music.R
import com.material.music.data.service.MusicNotificationProvider
<<<<<<< HEAD
import com.material.music.data.service.SleepTimerSession
=======
import com.material.music.data.service.SleepTimerReceiver
import com.material.music.data.EotStateHolder
>>>>>>> origin/main
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
<<<<<<< HEAD
import kotlinx.coroutines.launch
import timber.log.Timber
=======
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
>>>>>>> origin/main
import javax.inject.Inject
import javax.inject.Singleton

/**
<<<<<<< HEAD
 * Mirrors the sleep timers for the UI and forwards the user's choices to MusicService.
 *
 * The service ([com.material.music.data.service.SleepTimerController]) owns and
 * enforces every timer, since only it lives for the whole playback session and sees every
 * player swap; this holder only renders [SleepTimerSession] and toasts its events.
=======
 * Manages sleep timer state and operations.
 * Extracted from PlayerViewModel to improve modularity.
 *
 * Responsibilities:
 * - Duration-based sleep timer with AlarmManager
 * - End-of-track (EOT) timer functionality
 * - Counted play functionality
 * - Timer display state
>>>>>>> origin/main
 */
@Singleton
class SleepTimerStateHolder @Inject constructor(
    @ApplicationContext private val context: Context,
<<<<<<< HEAD
    private val session: SleepTimerSession,
) {
=======
) {
    private val _sleepTimerEndTimeMillis = MutableStateFlow<Long?>(null)

>>>>>>> origin/main
    private val _isEndOfTrackTimerActive = MutableStateFlow(value = false)
    val isEndOfTrackTimerActive: StateFlow<Boolean> = _isEndOfTrackTimerActive.asStateFlow()

    private val _activeTimerValueDisplay = MutableStateFlow<String?>(null)
    val activeTimerValueDisplay: StateFlow<String?> = _activeTimerValueDisplay.asStateFlow()

    /** When non-null, a duration-based sleep timer is active with this many minutes (matches [predefinedTimes]). */
    private val _activeTimerDurationMinutes = MutableStateFlow<Int?>(null)
    val activeTimerDurationMinutes: StateFlow<Int?> = _activeTimerDurationMinutes.asStateFlow()

    private val _playCount = MutableStateFlow(1f)
    val playCount: StateFlow<Float> = _playCount.asStateFlow()

<<<<<<< HEAD
    private var sessionMirrorJob: Job? = null
=======
    private var sleepTimerJob: Job? = null
    private var eotSongMonitorJob: Job? = null

    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
>>>>>>> origin/main

    private var scope: CoroutineScope? = null
    private var toastEmitter: (suspend (String) -> Unit)? = null
    private var mediaControllerProvider: (() -> MediaController?)? = null
<<<<<<< HEAD
    private var songTitleResolver: ((String?) -> String?)? = null
    private val owners = OwnerStack<Binding> { it.scope }

    /** One PlayerViewModel's dependencies, kept so the holder can fall back to them. */
    private class Binding(
        val scope: CoroutineScope,
        val toastEmitter: suspend (String) -> Unit,
        val mediaControllerProvider: () -> MediaController?,
        val songTitleResolver: (String?) -> String?,
    )
=======
    private var currentSongIdProvider: (() -> StateFlow<String?>)? = null
    private var songTitleResolver: ((String?) -> String)? = null

    private fun sleepTimerPendingIntent(): PendingIntent {
        val intent = Intent(context, SleepTimerReceiver::class.java).apply {
            action = SLEEP_TIMER_ACTION
            setPackage(context.packageName)
        }
        return PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
>>>>>>> origin/main

    /**
     * Initialize with dependencies from ViewModel.
     * Must be called before using timer functions.
     */
    fun initialize(
        scope: CoroutineScope,
        toastEmitter: suspend (String) -> Unit,
        mediaControllerProvider: () -> MediaController?,
<<<<<<< HEAD
        songTitleResolver: (String?) -> String?,
    ) {
        val binding = Binding(scope, toastEmitter, mediaControllerProvider, songTitleResolver)
        owners.push(binding)
        bind(binding)
    }

    private fun bind(binding: Binding) {
        scope = binding.scope
        toastEmitter = binding.toastEmitter
        mediaControllerProvider = binding.mediaControllerProvider
        songTitleResolver = binding.songTitleResolver

        sessionMirrorJob?.cancel()
        sessionMirrorJob = binding.scope.launch {
            launch { session.timer.collect { renderTimer(it) } }
            launch {
                session.countedPlay.collect { countedPlay ->
                    _playCount.value = countedPlay?.total?.toFloat() ?: 1f
                }
            }
            launch { session.events.collect { toastFor(it) } }
        }
    }

    private fun renderTimer(timer: SleepTimerSession.Timer?) {
        _isEndOfTrackTimerActive.value = timer is SleepTimerSession.Timer.EndOfTrack
        _activeTimerDurationMinutes.value = (timer as? SleepTimerSession.Timer.Duration)?.minutes
        _activeTimerValueDisplay.value = when (timer) {
            is SleepTimerSession.Timer.Duration ->
                context.getString(R.string.sleep_timer_n_minutes_format, timer.minutes)
            is SleepTimerSession.Timer.EndOfTrack ->
                context.getString(R.string.sleep_timer_display_eot)
            null -> null
        }
    }

    private suspend fun toastFor(event: SleepTimerSession.Event) {
        val message = when (event) {
            is SleepTimerSession.Event.EndOfTrackReached -> context.getString(
                R.string.player_playback_stopped_eot,
                songTitleResolver?.invoke(event.songId)
                    ?: context.getString(R.string.player_default_track_title)
            )
            is SleepTimerSession.Event.EndOfTrackCancelledBySongChange -> context.getString(
                R.string.sleep_timer_eot_song_changed_toast,
                songTitleResolver?.invoke(event.targetSongId)
                    ?: context.getString(R.string.sleep_timer_label_previous_track),
                songTitleResolver?.invoke(event.newSongId)
                    ?: context.getString(R.string.sleep_timer_label_current_track)
            )
            SleepTimerSession.Event.DurationExpired,
            is SleepTimerSession.Event.CountedPlayFinished -> null
        }
        message?.let { toastEmitter?.invoke(it) }
    }

    private fun sendToService(action: String, args: Bundle = Bundle.EMPTY): Boolean {
        val controller = mediaControllerProvider?.invoke() ?: run {
            Timber.w("Sleep timer command %s dropped: no MediaController", action)
            return false
        }
        controller.sendCustomCommand(SessionCommand(action, Bundle.EMPTY), args)
        return true
    }

    private fun toast(message: String) {
        val scope = this.scope ?: return
        scope.launch { toastEmitter?.invoke(message) }
    }

    /**
     * Set a duration-based sleep timer. Replaces an end-of-track timer.
     */
    fun setSleepTimer(durationMinutes: Int) {
        val sent = sendToService(
            MusicNotificationProvider.CUSTOM_COMMAND_SET_SLEEP_TIMER_DURATION,
            Bundle().apply { putInt(MusicNotificationProvider.EXTRA_SLEEP_TIMER_MINUTES, durationMinutes) }
        )
        if (sent) {
            toast(context.getString(R.string.sleep_timer_set_for_minutes_toast, durationMinutes))
=======
        currentSongIdProvider: () -> StateFlow<String?>,
        songTitleResolver: (String?) -> String
    ) {
        this.scope = scope
        this.toastEmitter = toastEmitter
        this.mediaControllerProvider = mediaControllerProvider
        this.currentSongIdProvider = currentSongIdProvider
        this.songTitleResolver = songTitleResolver
    }

    /**
     * Set a duration-based sleep timer.
     * Uses AlarmManager for reliability + coroutine for UI state clearing.
     */
    fun setSleepTimer(durationMinutes: Int) {
        val scope = this.scope ?: return

        if (_isEndOfTrackTimerActive.value) {
            eotSongMonitorJob?.cancel()
            cancelSleepTimer(suppressDefaultToast = true)
        }

        sleepTimerJob?.cancel()
        val durationMillis = TimeUnit.MINUTES.toMillis(durationMinutes.toLong())
        val endTime = System.currentTimeMillis() + durationMillis
        _sleepTimerEndTimeMillis.value = endTime
        _activeTimerDurationMinutes.value = durationMinutes
        _activeTimerValueDisplay.value = context.getString(
            R.string.sleep_timer_n_minutes_format,
            durationMinutes
        )

        val pendingIntent = sleepTimerPendingIntent()

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        endTime,
                        pendingIntent,
                    )
                } else {
                    alarmManager.setAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        endTime,
                        pendingIntent,
                    )
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    endTime,
                    pendingIntent,
                )
            }
        } catch (e: SecurityException) {
            Timber.e(e, "Failed to schedule exact alarm for sleep timer")
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                endTime,
                pendingIntent,
            )
        }

        scope.launch {
            toastEmitter?.invoke(
                context.getString(R.string.sleep_timer_set_for_minutes_toast, durationMinutes)
            )
>>>>>>> origin/main
        }
    }

    /**
<<<<<<< HEAD
     * Start counted play mode - play the current song N times, then pause.
     */
    fun playCounted(count: Int) {
        sendToService(
            MusicNotificationProvider.CUSTOM_COMMAND_COUNTED_PLAY,
            Bundle().apply { putInt("count", count) }
=======
     * Start counted play mode - play N more tracks.
     */
    fun playCounted(count: Int) {
        _playCount.value = count.toFloat()
        val args = Bundle().apply { putInt("count", count) }
        mediaControllerProvider?.invoke()?.sendCustomCommand(
            SessionCommand(MusicNotificationProvider.CUSTOM_COMMAND_COUNTED_PLAY, Bundle.EMPTY),
            args
>>>>>>> origin/main
        )
    }

    /**
     * Cancel counted play mode.
     */
    fun cancelCountedPlay() {
<<<<<<< HEAD
        sendToService(MusicNotificationProvider.CUSTOM_COMMAND_CANCEL_COUNTED_PLAY)
=======
        val args = Bundle()
        _playCount.value = 1f
        mediaControllerProvider?.invoke()?.sendCustomCommand(
            SessionCommand(MusicNotificationProvider.CUSTOM_COMMAND_CANCEL_COUNTED_PLAY, Bundle.EMPTY),
            args
        )
>>>>>>> origin/main
    }

    /**
     * Set or cancel end-of-track timer.
     */
    fun setEndOfTrackTimer(enable: Boolean, currentSongId: String?) {
<<<<<<< HEAD
        if (!enable) {
            // Cancel whenever the flag is active, so the toggle can never get stuck ON.
            if (_isEndOfTrackTimerActive.value) cancelSleepTimer()
            return
        }
        if (currentSongId == null) {
            toast(context.getString(R.string.sleep_timer_eot_no_song_toast))
            return
        }
        val sent = sendToService(
            MusicNotificationProvider.CUSTOM_COMMAND_SET_SLEEP_TIMER_END_OF_TRACK,
            Bundle().apply { putBoolean(MusicNotificationProvider.EXTRA_END_OF_TRACK_ENABLED, true) }
        )
        if (sent) {
            toast(context.getString(R.string.sleep_timer_eot_stop_at_end_toast))
=======
        val scope = this.scope ?: return

        if (enable) {
            if (currentSongId == null) {
                scope.launch {
                    toastEmitter?.invoke(context.getString(R.string.sleep_timer_eot_no_song_toast))
                }
                return
            }

            _activeTimerDurationMinutes.value = null
            _activeTimerValueDisplay.value = context.getString(R.string.sleep_timer_display_eot)
            _isEndOfTrackTimerActive.value = true
            EotStateHolder.setEotTargetSong(currentSongId)

            sleepTimerJob?.cancel()
            _sleepTimerEndTimeMillis.value = null

            eotSongMonitorJob?.cancel()
            eotSongMonitorJob = scope.launch {
                currentSongIdProvider?.invoke()
                    ?.filterNotNull()
                    ?.collect { newSongId ->
                        if (_isEndOfTrackTimerActive.value &&
                            (EotStateHolder.eotTargetSongId.value != null) &&
                            (newSongId != EotStateHolder.eotTargetSongId.value)
                        ) {

                        val oldSongTitle = songTitleResolver?.invoke(EotStateHolder.eotTargetSongId.value)
                            ?: context.getString(R.string.sleep_timer_label_previous_track)
                        val newSongTitle = songTitleResolver?.invoke(newSongId)
                            ?: context.getString(R.string.sleep_timer_label_current_track)

                        toastEmitter?.invoke(
                            context.getString(
                                R.string.sleep_timer_eot_song_changed_toast,
                                oldSongTitle,
                                newSongTitle
                            )
                        )
                        cancelSleepTimer(suppressDefaultToast = true)

                        eotSongMonitorJob?.cancel()
                        eotSongMonitorJob = null
                    }
                }
            }

            scope.launch {
                toastEmitter?.invoke(context.getString(R.string.sleep_timer_eot_stop_at_end_toast))
            }
        } else {
            eotSongMonitorJob?.cancel()
            if (_isEndOfTrackTimerActive.value && EotStateHolder.eotTargetSongId.value != null) {
                cancelSleepTimer()
            }
>>>>>>> origin/main
        }
    }

    /**
     * Cancel any active sleep timer or EOT timer.
     */
    fun cancelSleepTimer(overrideToastMessage: String? = null, suppressDefaultToast: Boolean = false) {
<<<<<<< HEAD
        val wasAnythingActive = session.timer.value != null
        sendToService(MusicNotificationProvider.CUSTOM_COMMAND_CANCEL_SLEEP_TIMER)

        when {
            overrideToastMessage != null -> toast(overrideToastMessage)
            !suppressDefaultToast && wasAnythingActive ->
                toast(context.getString(R.string.sleep_timer_cancelled_toast))
=======
        val scope = this.scope ?: return
        val wasAnythingActive = _activeTimerValueDisplay.value != null

        val pendingIntent = sleepTimerPendingIntent()
        alarmManager.cancel(pendingIntent)

        sleepTimerJob?.cancel()
        sleepTimerJob = null
        _sleepTimerEndTimeMillis.value = null

        eotSongMonitorJob?.cancel()
        eotSongMonitorJob = null
        _isEndOfTrackTimerActive.value = false
        EotStateHolder.setEotTargetSong(null)

        _activeTimerDurationMinutes.value = null
        _activeTimerValueDisplay.value = null

        when {
            overrideToastMessage != null -> scope.launch { toastEmitter?.invoke(overrideToastMessage) }
            !suppressDefaultToast && wasAnythingActive -> scope.launch {
                toastEmitter?.invoke(context.getString(R.string.sleep_timer_cancelled_toast))
            }
>>>>>>> origin/main
        }
    }

    /**
<<<<<<< HEAD
     * Cleanup when ViewModel is cleared. A stale PlayerViewModel clearing leaves the newer
     * binding alone; the current one clearing re-binds the previous live PlayerViewModel and
     * restarts the session mirror in its scope. Nulling the callbacks while a PlayerViewModel
     * is still alive would make the timer display, toasts and end-of-track toggle silently
     * no-op until process restart.
     */
    fun onCleared(owner: CoroutineScope) {
        owners.remove(owner) { next ->
            if (next != null) {
                bind(next)
            } else {
                sessionMirrorJob?.cancel()
                sessionMirrorJob = null
                scope = null
                toastEmitter = null
                mediaControllerProvider = null
                songTitleResolver = null
            }
        }
=======
     * Cleanup when ViewModel is cleared. Only the scope that currently owns the binding
     * may sever it: a stale PlayerViewModel clearing after a newer instance already
     * re-initialized this singleton must not null the live callbacks (the timer toasts
     * and end-of-track toggle would silently no-op until process restart).
     */
    fun onCleared(owner: CoroutineScope) {
        if (scope !== owner) return
        sleepTimerJob?.cancel()
        eotSongMonitorJob?.cancel()
        scope = null
        toastEmitter = null
        mediaControllerProvider = null
        currentSongIdProvider = null
        songTitleResolver = null
    }

    private companion object {
        const val SLEEP_TIMER_ACTION = "com.material.music.action.SLEEP_TIMER_EXPIRED"
>>>>>>> origin/main
    }
}
