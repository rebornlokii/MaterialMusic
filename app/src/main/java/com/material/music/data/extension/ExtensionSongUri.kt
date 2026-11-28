package com.material.music.data.extension

import java.net.URLEncoder

/**
 * Canonical identity for an online track: `mornye://<provider>:<trackId>`.
 *
 * The scheme is what the rest of the app keys on (source type, offline lookups,
 * playback resolution); the provider and track id are everything the extension
 * host needs to re-resolve the track later.
 */
object ExtensionSongUri {
    private const val PREFIX = "mornye://"

    fun build(providerId: String, trackId: String): String =
        PREFIX + encode(providerId) + ":" + encode(trackId)

    /** Returns `providerId` to `trackId`, or null when [uri] is not a Mornye URI. */
    fun parse(uri: String?): Pair<String, String>? {
        if (uri == null || !uri.startsWith(PREFIX)) return null
        val body = uri.removePrefix(PREFIX)
        val separator = body.indexOf(':')
        if (separator <= 0 || separator == body.lastIndex) return null
        val provider = decode(body.substring(0, separator))
        val trackId = decode(body.substring(separator + 1))
        if (provider.isBlank() || trackId.isBlank()) return null
        return provider to trackId
    }

    private fun encode(value: String): String = URLEncoder.encode(value, "UTF-8").replace("+", "%20")

    private fun decode(value: String): String =
        runCatching { java.net.URLDecoder.decode(value, "UTF-8") }.getOrDefault(value)
}