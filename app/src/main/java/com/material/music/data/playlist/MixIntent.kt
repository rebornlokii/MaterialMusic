package com.material.music.data.playlist

/**
 * How a request's meaning was resolved.
 *
 * [Local] is the device's own parser: no network, no key, always available. [Refined] means an
 * optional online service rewrote the request into the engine's own vocabulary first, which the
 * local parser then consumed like any other prompt. The distinction is recorded for diagnostics
 * only — callers and the composer behave identically either way.
 */
enum class MixIntentSource {
    Local,
    Refined,
}

/**
 * A request that is ready to be scored against the library.
 *
 * [prompt] is the text the engine parses. A refined request carries the same meaning expressed in
 * the terms the engine actually understands, with the composer's length/energy directives kept
 * exactly as the caller built them, so the hard constraints are never up for interpretation.
 *
 * This deliberately says nothing about *which* songs to pick. Selection, ranking and sequencing
 * stay with the offline engine and the library it can see; a refinement only ever improves the
 * words the request is expressed in, and cannot name, add or remove a song.
 */
data class MixIntent(
    val prompt: String,
    val source: MixIntentSource = MixIntentSource.Local,
)