package com.material.music.data.extension

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.File
import java.util.zip.ZipFile

/**
 * The `.sflx` packages shipped inside the APK (`assets/extensions/`).
 *
 * They are copied to cache and installed on first run, so the app has working
 * providers before it ever touches the network.
 */
object ExtensionAssets {

    private const val DIR = "extensions"

    /** Asset file names, e.g. `tidal-web.sflx`. */
    fun list(context: Context): List<String> =
        context.assets.list(DIR)?.filter { it.endsWith(".sflx") }?.sorted().orEmpty()

    /** Copies one bundled package to cache and returns the file to install. */
    fun copy(context: Context, name: String): File {
        val target = File(context.cacheDir, "bundled_extensions/$name")
        target.parentFile?.mkdirs()
        context.assets.open("$DIR/$name").use { input ->
            target.outputStream().use { output -> input.copyTo(output) }
        }
        return target
    }

    private fun manifest(file: File, gson: Gson): JsonObject? = runCatching {
        ZipFile(file).use { zip ->
            val entry = zip.getEntry("manifest.json") ?: return null
            zip.getInputStream(entry).bufferedReader().use { gson.fromJson(it, JsonObject::class.java) }
        }
    }.getOrNull()

    fun name(file: File, gson: Gson): String? =
        manifest(file, gson)?.get("name")?.asString?.takeIf { it.isNotBlank() }

    fun version(file: File, gson: Gson): String? =
        manifest(file, gson)?.get("version")?.asString?.takeIf { it.isNotBlank() }
}