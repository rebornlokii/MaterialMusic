package com.material.music.data.extension

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.extensionDownloadDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "extension_download_settings"
)

data class ExtensionDownloadPrefs(
    /** Empty means the app-private downloads folder. */
    val treeUri: String? = null,
    val quality: String = "LOSSLESS",
    val template: String = "{artist}/{album}/{track:02} - {title}",
    val embedLyrics: Boolean = true,
)

/**
 * Download destination and quality for extension downloads.
 *
 * A user-visible folder is a SAF tree; when none is picked the files stay in the
 * app-private downloads directory, which is the safe default.
 */
@Singleton
class ExtensionDownloadSettings @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val keys = object {
        val tree = stringPreferencesKey("tree_uri")
        val quality = stringPreferencesKey("quality")
        val template = stringPreferencesKey("template")
        val embedLyrics = booleanPreferencesKey("embed_lyrics")
    }

    val prefs: Flow<ExtensionDownloadPrefs> = context.extensionDownloadDataStore.data.map { value ->
        ExtensionDownloadPrefs(
            treeUri = value[keys.tree]?.takeIf { it.isNotBlank() },
            quality = value[keys.quality] ?: "LOSSLESS",
            template = value[keys.template] ?: "{artist}/{album}/{track:02} - {title}",
            embedLyrics = value[keys.embedLyrics] ?: true,
        )
    }

    suspend fun snapshot(): ExtensionDownloadPrefs = prefs.first()

    suspend fun setTreeUri(uri: String?) = context.extensionDownloadDataStore.edit {
        if (uri.isNullOrBlank()) it.remove(keys.tree) else it[keys.tree] = uri
    }

    suspend fun setQuality(quality: String) = context.extensionDownloadDataStore.edit {
        it[keys.quality] = quality
    }

    suspend fun setTemplate(template: String) = context.extensionDownloadDataStore.edit {
        it[keys.template] = template
    }

    suspend fun setEmbedLyrics(enabled: Boolean) = context.extensionDownloadDataStore.edit {
        it[keys.embedLyrics] = enabled
    }
}