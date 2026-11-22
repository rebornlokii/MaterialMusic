package com.material.music.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A one-shot answer to "is there a usable internet connection right now?".
 *
 * Deliberately a plain query rather than a subscribed flow: the only caller wants to know at the
 * moment of a request whether it is worth attempting a network call, and the system already
 * answers that synchronously. `VALIDATED` is required, so a captive portal or a network that has
 * not finished its connectivity check counts as offline rather than as a connection that will
 * hang until timeout.
 *
 * Reading this must never fail a request: any doubt answers `false`, and the caller carries on
 * with its offline path.
 */
@Singleton
class NetworkConnectivity @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val connectivityManager: ConnectivityManager? =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

    fun isOnline(): Boolean = runCatching {
        val manager = connectivityManager ?: return false
        val capabilities = manager.getNetworkCapabilities(manager.activeNetwork) ?: return false
        capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }.getOrDefault(false)
}