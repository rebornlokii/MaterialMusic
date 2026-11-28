package com.material.music.di

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.material.music.BuildConfig
import com.material.music.data.extension.ExtensionHost
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.Base64
import javax.inject.Singleton

/**
 * Provides the Mornye backend host.
 *
 * The native library is loaded lazily on first call, so the app still starts on
 * devices where the `.so` is missing (the host simply fails on first use).
 */
@Module
@InstallIn(SingletonComponent::class)
object ExtensionModule {

    private const val PREFS = "mornye_extension_prefs"
    private const val KEY_MASTER = "master_key"

    /** Mirrors the app's shipped default provider order. */
    private val DEFAULT_PROVIDERS = listOf(
        "apple_music",
        "lrcred",
        "mornye_lyrics",
        "lyricsplus",
        "lrclib",
    )

    @Provides
    @Singleton
    fun provideExtensionHost(@ApplicationContext context: Context): ExtensionHost {
        val root = context.filesDir.resolve("mornye")
        val extensions = root.resolve("extensions").apply { mkdirs() }
        val data = root.resolve("extension_data").apply { mkdirs() }

        return ExtensionHost(
            sourceDirectory = extensions.absolutePath,
            dataDirectory = data.absolutePath,
            masterKey = loadOrCreateMasterKey(context),
            appVersion = BuildConfig.VERSION_NAME,
            providersJson = DEFAULT_PROVIDERS.joinToString(prefix = "[", postfix = "]", transform = ::quote),
            optionsJson = "{}",
        )
    }

    private fun quote(value: String): String = "\"$value\""

    private fun loadOrCreateMasterKey(context: Context): String {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        val prefs = EncryptedSharedPreferences.create(
            context,
            PREFS,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
        prefs.getString(KEY_MASTER, null)?.let { return it }
        val bytes = ByteArray(32).also { java.security.SecureRandom().nextBytes(it) }
        return Base64.getEncoder().encodeToString(bytes).also {
            prefs.edit().putString(KEY_MASTER, it).apply()
        }
    }
}