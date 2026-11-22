package com.material.music.data.playlist

import com.material.music.data.playlist.nlp.GenreFamily
import com.material.music.data.playlist.nlp.MoodProfile
import com.material.music.data.playlist.nlp.NlpText

/**
 * The pure half of request refinement: what to ask an optional online service, and what to
 * accept back from it. No Android, no network, no key — so the rules that decide whether a
 * refinement is allowed to change a request can be tested directly.
 *
 * The contract these rules enforce, in order of importance:
 *
 *  1. **A refinement only ever restates.** It returns words, never songs, and never a
 *     requirement the user did not state. Selection stays with the offline engine.
 *  2. **The composer's directives survive untouched.** Length and energy are constraints set on
 *     a control, so they are stripped before the request is restated and re-attached verbatim
 *     after, never offered to the service to interpret.
 *  3. **Doubt means discard.** The reply is untrusted model output. Anything empty, long,
 *     marked-up, wordless or negation-flipping is dropped, and the original request is used.
 */
object MixRequestRefinement {

    /** Long enough for a slow model, short enough that the composer never looks stuck. */
    const val TIMEOUT_MS = 6_000L

    /** A restatement is one short line; anything longer is a paragraph the parser will misread. */
    const val MAX_RESTATEMENT_LENGTH = 240

    private val MARKUP_CHARACTERS = charArrayOf('{', '}', '<', '>', '`', '#', '*')

    /**
     * Matches a negation cue in either supported language. Used only to check that a restatement
     * has not turned "no metal" into "metal", or the reverse.
     */
    private val NEGATION = Regex(
        "(?i)\\b(?:no|not|without|except|excluding|avoid|skip|sin|menos|excepto|salvo|nada)\\b",
    )

    /**
     * The system prompt: restate the request, invent nothing, name no songs.
     *
     * The vocabulary lists are generated from the enums the engine actually parses, so this
     * prompt cannot drift away from the parser it feeds.
     */
    val SYSTEM_PROMPT: String = buildString {
        appendLine(
            "You rewrite a music-listening request into plain, simple wording for an offline " +
                "music library search engine. The engine matches words against song tags " +
                "(title, artist, album, genre) and against a small mood and genre vocabulary. " +
                "It does not understand slang, references or long sentences.",
        )
        appendLine()
        appendLine("Rewrite the user's request as ONE short line of simple words.")
        appendLine("Rules:")
        appendLine("- Keep the same meaning exactly. Never add a mood, genre, era, artist, tempo or length the user did not ask for.")
        appendLine("- Keep every condition the user did state, including anything they said they do NOT want.")
        appendLine("- Prefer one of these words wherever one fits:")
        append("  genres: ")
        appendLine(GENRE_VOCABULARY)
        append("  moods: ")
        appendLine(MOOD_VOCABULARY)
        appendLine("- Use plain words for everything else: a feeling, an activity, a place, a time of day, a decade such as 90s.")
        appendLine("- Never name a song, an album or an artist. Only the user's own words may name one.")
        appendLine("- Answer with the rewritten line only. No quotes, no labels, no explanation.")
    }

    /** Genre families, spelled the way a person would write them. */
    private val GENRE_VOCABULARY: String = GenreFamily.entries
        .joinToString(", ") { it.name.lowercase().replace('_', ' ') }

    /** Mood profiles, likewise. */
    private val MOOD_VOCABULARY: String = MoodProfile.entries
        .joinToString(", ") { it.name.lowercase().replace('_', ' ') }

    /** The role names the service expects. */
    const val ROLE_SYSTEM = "system"
    const val ROLE_USER = "user"

    /**
     * Accepts only a short, single-line restatement that keeps the request's meaning, or null
     * when the reply should be ignored.
     *
     * [original] is the user's free text as typed, with the composer's directives already
     * removed — that is the text the restatement has to stay faithful to.
     */
    fun sanitize(reply: String, original: String): String? {
        val text = reply.lineSequence()
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .firstOrNull()
            ?.trim('"', '\'')
            ?.trim()
            ?: return null
        if (text.isEmpty() || text.length > MAX_RESTATEMENT_LENGTH) return null
        if (text.any { it in MARKUP_CHARACTERS }) return null
        // A line with no words left is not a restatement of anything.
        if (NlpText.tokenize(text).isEmpty()) return null
        // Flipping a negation flips the request's meaning.
        if (NEGATION.containsMatchIn(text) != NEGATION.containsMatchIn(original)) return null
        return text
    }

    /**
     * The restated request: [freeText] replaced by [restatement], with the prompt's directives
     * re-attached exactly as they were. A blank restatement yields the original [prompt].
     */
    fun apply(prompt: String, restatement: String?): String {
        val directive = MixDirective.of(prompt)
        if (restatement.isNullOrBlank()) return prompt
        return buildString {
            append(restatement)
            append(directive.render())
        }.trim()
    }
}

/**
 * The structured parts of a request the composer appends as directives. The engine treats them as
 * hard constraints, so they are carried around a refinement untouched rather than rewritten.
 */
data class MixDirective(
    val targetBpm: Int? = null,
    val targetLength: Int? = null,
) {
    /** The user's own words, with these directives removed. */
    fun freeTextOf(prompt: String): String = prompt.replace(BPM, " ").replace(LENGTH, " ").trim()

    /** Appended in the form [com.material.music.data.playlist.nlp.PlaylistIntentEngine] parses. */
    fun render(): String = buildString {
        targetBpm?.let { append("\n[Target BPM: $it BPM]") }
        targetLength?.let { append("\n[Target Length: $it songs]") }
    }

    /** True when the prompt carries neither control, i.e. there is nothing to preserve. */
    fun isEmpty(): Boolean = targetBpm == null && targetLength == null

    companion object {
        private val BPM = Regex("(?i)\\[\\s*target\\s+bpm\\s*:\\s*(\\d+)[^\\]]*\\]")
        private val LENGTH = Regex("(?i)\\[\\s*target\\s+length\\s*:\\s*(\\d+)[^\\]]*\\]")

        /** The directives found in a prompt the composer built. */
        fun of(prompt: String): MixDirective = MixDirective(
            targetBpm = BPM.find(prompt)?.groupValues?.get(1)?.toIntOrNull(),
            targetLength = LENGTH.find(prompt)?.groupValues?.get(1)?.toIntOrNull(),
        )
    }
}