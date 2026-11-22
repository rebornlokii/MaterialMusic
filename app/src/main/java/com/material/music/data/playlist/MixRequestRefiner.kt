package com.material.music.data.playlist

import com.material.music.BuildConfig
import com.material.music.data.network.NetworkConnectivity
import com.material.music.data.network.openrouter.OpenRouterApiService
import com.material.music.data.network.openrouter.OpenRouterChatRequest
import com.material.music.data.network.openrouter.OpenRouterMessage
import dagger.Lazy
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Rewrites a request into the vocabulary [com.material.music.data.playlist.nlp.PlaylistIntentEngine]
 * already understands, so the offline parser can score it without having to understand the user's
 * phrasing itself.
 *
 * This is a *narrow* job on purpose, and the split is what keeps it safe:
 *
 *  - The service never sees the library, a song or an artist. It is sent the request plus the
 *    engine's own vocabulary, and it returns one short line.
 *  - The service never chooses songs. It returns text. Which songs exist, which of them match and
 *    in what order they play is decided locally by [NlpPlaylistGenerator] against the library on
 *    this device.
 *  - A refinement can only ever *restate* the request. If it invented a requirement the user did
 *    not ask for, the local engine would treat that as one, so the reply is validated by
 *    [MixRequestRefinement] and anything doubtful is dropped.
 *
 * Every failure degrades silently: no connection, a timeout, a malformed or unhelpful reply, a
 * cancelled request — each returns the prompt untouched. Nothing here throws, so callers never
 * need a fallback path of their own.
 */
@Singleton
class MixRequestRefiner @Inject constructor(
    private val apiService: Lazy<OpenRouterApiService>,
    private val connectivity: NetworkConnectivity,
) {
    /**
     * A prompt ready for the engine.
     *
     * The composer's structured directives (length, energy) are stripped before the request is
     * restated and re-attached verbatim afterwards: those are constraints the user set on a
     * control, not prose for a model to interpret.
     *
     * Returns [MixIntentSource.Local] untouched when there is no free text, no key compiled in,
     * or no usable connection — which is also the whole offline behaviour.
     */
    suspend fun refine(prompt: String): MixIntent {
        val directive = MixDirective.of(prompt)
        val freeText = directive.freeTextOf(prompt)
        // Nothing to restate, nothing configured, or no connection: the local parser sees
        // exactly what was typed.
        if (freeText.isEmpty() || !isConfigured || !connectivity.isOnline()) {
            return MixIntent(prompt, MixIntentSource.Local)
        }

        val reply = withContext(Dispatchers.IO) {
            withTimeoutOrNull(MixRequestRefinement.TIMEOUT_MS) {
                runCatching {
                    apiService.get().chatCompletions(
                        authorization = "Bearer ${BuildConfig.OPENROUTER_API_KEY}",
                        request = OpenRouterChatRequest(
                            model = BuildConfig.OPENROUTER_MODEL,
                            messages = listOf(
                                OpenRouterMessage(
                                    MixRequestRefinement.ROLE_SYSTEM,
                                    MixRequestRefinement.SYSTEM_PROMPT,
                                ),
                                OpenRouterMessage(MixRequestRefinement.ROLE_USER, freeText),
                            ),
                        ),
                    ).message
                }.onFailure { error ->
                    // Cancellation must keep unwinding; anything else is just a failed assist.
                    if (error is CancellationException) throw error
                    // The message and type only: a network exception's detail can carry the
                    // request, and the request carries the Authorization header.
                    Timber.tag(TAG).w(
                        "Request refinement failed (%s); using local parsing",
                        error.javaClass.simpleName,
                    )
                }.getOrNull()
            }
        } ?: return MixIntent(prompt, MixIntentSource.Local)

        val restated = MixRequestRefinement.sanitize(reply, freeText)
            ?: return MixIntent(prompt, MixIntentSource.Local)
        return MixIntent(MixRequestRefinement.apply(prompt, restated), MixIntentSource.Refined)
    }

    /** True when a key is compiled in, i.e. refinement is possible at all. */
    private val isConfigured: Boolean get() = BuildConfig.OPENROUTER_API_KEY.isNotBlank()

    private companion object {
        const val TAG = "MixRequestRefiner"
    }
}