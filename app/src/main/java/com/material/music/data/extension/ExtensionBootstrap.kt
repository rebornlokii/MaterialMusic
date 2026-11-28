package com.material.music.data.extension

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Installs the bundled packages and keeps them enabled.
 *
 * Idempotent: a package is reinstalled only when its manifest version changes,
 * so this is cheap to call on every app start.
 */
@Singleton
class ExtensionBootstrap @Inject constructor(
    @ApplicationContext private val context: Context,
    private val host: ExtensionHost,
    private val gson: Gson,
) {

    private val prefs by lazy {
        context.getSharedPreferences("mornye_extensions", Context.MODE_PRIVATE)
    }

    /** Version of each extension this app installed, by manifest name. */
    fun installedVersion(id: String): String? = prefs.getString("installed:$id", null)

    fun markInstalled(id: String, version: String) {
        prefs.edit().putString("installed:$id", version).apply()
    }

    /** Installs every bundled package that is missing or older than the APK's copy. */
    suspend fun installBundled() {
        var installed = 0
        for (asset in ExtensionAssets.list(context)) {
            val file = runCatching { ExtensionAssets.copy(context, asset) }.getOrElse {
                Timber.w(it, "Could not stage bundled extension %s", asset)
                continue
            }
            val id = ExtensionAssets.name(file, gson) ?: continue
            val version = ExtensionAssets.version(file, gson) ?: continue

            if (installedVersion(id) == version) {
                runCatching { host.setEnabled(id, true) }
                continue
            }
            runCatching {
                host.install(file.absolutePath)
                host.setEnabled(id, true)
                markInstalled(id, version)
                installed++
            }.onFailure { Timber.w(it, "Could not install bundled extension %s", id) }
        }
        // An empty fallback list means "try every enabled provider", which is
        // the default the engine ships with.
        runCatching { host.setFallbackProviders(null) }
        if (installed > 0) {
            runCatching { host.loadAll() }
            Timber.i("Installed %d bundled extensions", installed)
        }
    }
}

/** Registry entry from `registry.json`. */
data class ExtensionRegistryEntry(
    val id: String,
    val version: String,
    val downloadUrl: String,
    val sha256: String?,
    val minAppVersion: String?,
)

/** Parses the extension store catalog. */
object ExtensionRegistry {

    const val DEFAULT_URL =
        "https://raw.githubusercontent.com/mornyeapp/Mornye-Extension/main/registry.json"

    fun parse(raw: String, gson: Gson): List<ExtensionRegistryEntry> {
        val root = gson.fromJson(raw, JsonObject::class.java) ?: return emptyList()
        val entries = root.getAsJsonArray("extensions") ?: return emptyList()
        return entries.mapNotNull { element ->
            val entry = element.asJsonObject
            val id = entry.get("id")?.asString ?: return@mapNotNull null
            val version = entry.get("version")?.asString ?: return@mapNotNull null
            val url = entry.get("download_url")?.asString ?: return@mapNotNull null
            // The JS catalog must never hand us a Python-runtime package.
            val runtime = entry.get("runtime")?.asString
            if (runtime != null && runtime != "javascript") return@mapNotNull null
            ExtensionRegistryEntry(
                id = id,
                version = version,
                downloadUrl = url,
                sha256 = entry.get("sha256")?.asString,
                minAppVersion = entry.get("min_app_version")?.asString,
            )
        }
    }
}

/** Compares dotted numeric versions ("1.10.6" vs "1.9.0"). */
fun isNewerVersion(candidate: String, current: String): Boolean {
    val a = candidate.split('.').map { it.toIntOrNull() ?: 0 }
    val b = current.split('.').map { it.toIntOrNull() ?: 0 }
    for (i in 0 until maxOf(a.size, b.size)) {
        val x = a.getOrElse(i) { 0 }
        val y = b.getOrElse(i) { 0 }
        if (x != y) return x > y
    }
    return false
}