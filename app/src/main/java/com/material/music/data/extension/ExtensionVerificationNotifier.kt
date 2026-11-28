package com.material.music.data.extension

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.material.music.R

/**
 * Tells the user a download needs a browser sign-in.
 *
 * Signed-session providers (Tidal, Qobuz, Amazon, Beatport) require a grant;
 * the engine reports `verification_required` and the app surfaces it instead of
 * failing silently.
 */
object ExtensionVerificationNotifier {

    private const val CHANNEL = "extension_verification"
    private const val ID = 4101

    fun notify(context: Context, extensionId: String, message: String) {
        val manager = context.getSystemService(NotificationManager::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(CHANNEL, "Extension sign-in", NotificationManager.IMPORTANCE_HIGH)
            )
        }
        val notification = NotificationCompat.Builder(context, CHANNEL)
            .setSmallIcon(R.drawable.rounded_settings_24)
            .setContentTitle("Sign in to $extensionId")
            .setContentText(message)
            .setAutoCancel(true)
            .build()
        manager.notify(ID, notification)
    }
}