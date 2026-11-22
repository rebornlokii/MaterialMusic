package com.material.music.data.playlist

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

/**
 * Covers the rules that decide whether an optional refinement may change a request.
 *
 * These are the safety boundary of the feature: a refinement is model output, so the assertions
 * here are about what must never happen rather than about restatement quality. A reply that is
 * empty, long, marked-up or negation-flipping has to be dropped, and the composer's length and
 * energy controls have to survive a refinement exactly as they went in.
 */
class MixRequestRefinementTest {

    // --- directives -----------------------------------------------------------------------

    @Test
    fun `directives are read out of a composed prompt`() {
        val directive = MixDirective.of("workout music\n[Target BPM: 120 BPM]\n[Target Length: 25 songs]")

        assertThat(directive.targetBpm).isEqualTo(120)
        assertThat(directive.targetLength).isEqualTo(25)
    }

    @Test
    fun `a prompt without directives leaves both controls unset`() {
        val directive = MixDirective.of("something chill")

        assertThat(directive.targetBpm).isNull()
        assertThat(directive.targetLength).isNull()
        assertThat(directive.isEmpty()).isTrue()
        assertThat(directive.render()).isEmpty()
    }

    @Test
    fun `free text is the prompt with the controls removed`() {
        val freeText = MixDirective.of(
            "songs for the gym\n[Target BPM: 165 BPM]\n[Target Length: 50 songs]",
        ).freeTextOf("songs for the gym\n[Target BPM: 165 BPM]\n[Target Length: 50 songs]")

        assertThat(freeText).isEqualTo("songs for the gym")
        assertThat(freeText).doesNotContain("Target")
    }

    @Test
    fun `directives survive a restatement verbatim`() {
        val prompt = "melancholic stuff\n[Target BPM: 85 BPM]\n[Target Length: 15 songs]"

        val result = MixRequestRefinement.apply(prompt, "sad slow songs")

        assertThat(result).isEqualTo("sad slow songs\n[Target BPM: 85 BPM]\n[Target Length: 15 songs]")
    }

    @Test
    fun `a restatement of a prompt with no controls adds none`() {
        val result = MixRequestRefinement.apply("something heavy", "metal and hard rock")

        assertThat(result).isEqualTo("metal and hard rock")
        assertThat(result).doesNotContain("Target")
    }

    @Test
    fun `a blank restatement leaves the prompt exactly as it was`() {
        val prompt = "chillhop\n[Target BPM: 85 BPM]"

        assertThat(MixRequestRefinement.apply(prompt, null)).isEqualTo(prompt)
        assertThat(MixRequestRefinement.apply(prompt, "   ")).isEqualTo(prompt)
    }

    // --- accepting a reply ----------------------------------------------------------------

    @Test
    fun `accepts a plain single line`() {
        assertThat(MixRequestRefinement.sanitize("  mellow acoustic songs  ", "chill acoustic"))
            .isEqualTo("mellow acoustic songs")
    }

    @Test
    fun `takes the first line of a multi-line reply`() {
        val reply = """
            Here is the rewritten request:
            sad slow songs
        """.trimIndent()

        assertThat(MixRequestRefinement.sanitize(reply, "melancholy"))
            .isEqualTo("Here is the rewritten request:")
    }

    @Test
    fun `strips surrounding quotes`() {
        assertThat(MixRequestRefinement.sanitize("\"workout rock\"", "gym rock"))
            .isEqualTo("workout rock")
        assertThat(MixRequestRefinement.sanitize("'workout rock'", "gym rock"))
            .isEqualTo("workout rock")
    }

    @Test
    fun `an empty reply is dropped`() {
        assertThat(MixRequestRefinement.sanitize("", "chill")).isNull()
        assertThat(MixRequestRefinement.sanitize("   \n  ", "chill")).isNull()
        assertThat(MixRequestRefinement.sanitize("\"\"", "chill")).isNull()
    }

    @Test
    fun `a reply that runs long is dropped`() {
        // Longer than MAX_RESTATEMENT_LENGTH, which is the guard against a model answering with
        // a paragraph the parser would read as a pile of extra requirements.
        val rambling = (
            "a song about the way the light falls through the window in the afternoon " +
                "while the rain is coming down outside and everything feels quiet and far away " +
                "from the rest of the world and nobody needs anything from you any more today " +
                "and you can finally sit still and breathe out slowly"
            )

        assertThat(rambling.length).isGreaterThan(MixRequestRefinement.MAX_RESTATEMENT_LENGTH)
        assertThat(MixRequestRefinement.sanitize(rambling, "chill")).isNull()
    }

    @Test
    fun `a reply carrying markup is dropped`() {
        assertThat(MixRequestRefinement.sanitize("{\"request\": \"chill\"}", "chill")).isNull()
        assertThat(MixRequestRefinement.sanitize("**chill**", "chill")).isNull()
        assertThat(MixRequestRefinement.sanitize("# chill", "chill")).isNull()
        assertThat(MixRequestRefinement.sanitize("chill {slow}", "chill")).isNull()
    }

    @Test
    fun `a reply with no words is dropped`() {
        // No token carries a letter, so there is nothing for the parser to score.
        assertThat(MixRequestRefinement.sanitize("...", "chill")).isNull()
        assertThat(MixRequestRefinement.sanitize("42", "chill")).isNull()
        assertThat(MixRequestRefinement.sanitize("---", "chill")).isNull()
        assertThat(MixRequestRefinement.sanitize("   ", "chill")).isNull()
    }

    @Test
    fun `a single bulleted line is unwrapped rather than dropped`() {
        // A model that answers "- chill -" has still answered the question, so the dashes come
        // off instead of the whole reply being discarded.
        assertThat(MixRequestRefinement.sanitize("- chill -", "chill")).isEqualTo("chill")
        assertThat(MixRequestRefinement.sanitize("- mellow acoustic", "mellow acoustic"))
            .isEqualTo("mellow acoustic")
    }

    @Test
    fun `markup is checked before bullets are unwrapped`() {
        // "*chill*" is markdown emphasis, not a bullet, and ">" is a blockquote. Stripping
        // first would have accepted both as ordinary restatements.
        assertThat(MixRequestRefinement.sanitize("**chill**", "chill")).isNull()
        assertThat(MixRequestRefinement.sanitize("*chill*", "chill")).isNull()
        assertThat(MixRequestRefinement.sanitize("> chill", "chill")).isNull()
    }

    @Test
    fun `a reply that introduces a negation is dropped`() {
        assertThat(MixRequestRefinement.sanitize("rock but no metal", "rock")).isNull()
        assertThat(MixRequestRefinement.sanitize("sin metal", "rock")).isNull()
    }

    @Test
    fun `a reply that drops a negation is dropped`() {
        assertThat(MixRequestRefinement.sanitize("rock", "rock without metal")).isNull()
        assertThat(MixRequestRefinement.sanitize("rock", "no rock")).isNull()
    }

    @Test
    fun `a reply that keeps the negation is accepted`() {
        assertThat(MixRequestRefinement.sanitize("rock without metal", "rock no metal"))
            .isEqualTo("rock without metal")
    }

    // --- the prompt itself ----------------------------------------------------------------

    @Test
    fun `the prompt states the vocabulary the engine parses`() {
        val prompt = MixRequestRefinement.SYSTEM_PROMPT

        assertThat(prompt).contains("Never add")
        assertThat(prompt).contains("Never name a song")
        // The vocabulary the engine actually matches on: families are enum names, not genres.
        assertThat(prompt).contains("guitar")
        assertThat(prompt).contains("chill ambient")
        assertThat(prompt).contains("workout")
    }

    @Test
    fun `the prompt's vocabulary is derived from the engine enums`() {
        val prompt = MixRequestRefinement.SYSTEM_PROMPT

        // Every family the parser can infer is offered to the model, spelled readably.
        for (family in com.material.music.data.playlist.nlp.GenreFamily.entries) {
            val word = family.name.lowercase().replace('_', ' ')
            assertThat(prompt).contains(word)
        }
        for (mood in com.material.music.data.playlist.nlp.MoodProfile.entries) {
            val word = mood.name.lowercase().replace('_', ' ')
            assertThat(prompt).contains(word)
        }
    }

    @Test
    fun `the prompt is one system message with no user text in it`() {
        // The user's request travels as a separate user message. If the prompt ever quoted it,
        // the two could drift apart and the contract would no longer be readable in one place.
        val prompt = MixRequestRefinement.SYSTEM_PROMPT

        assertThat(MixRequestRefinement.ROLE_SYSTEM).isEqualTo("system")
        assertThat(MixRequestRefinement.ROLE_USER).isEqualTo("user")
        assertThat(prompt).doesNotContain("ROLE_USER")
    }
}