package com.material.music.data.network.openrouter

import com.google.gson.annotations.SerializedName

/**
 * One chat completion request. Only the fields this app sends are modelled; everything else is
 * left to the service's defaults.
 */
data class OpenRouterChatRequest(
    val model: String,
    val messages: List<OpenRouterMessage>,
    val temperature: Double = 0.2,
    val max_tokens: Int = 300,
)

data class OpenRouterMessage(
    val role: String,
    val content: String,
)

data class OpenRouterChatResponse(
    val choices: List<OpenRouterChoice> = emptyList(),
    val error: OpenRouterError? = null,
) {
    /** The assistant's text, or null when the service refused, filtered or returned nothing. */
    val message: String?
        get() = choices.firstOrNull()?.message?.content?.takeIf { it.isNotBlank() }
}

data class OpenRouterChoice(
    val message: OpenRouterMessage? = null,
)

data class OpenRouterError(
    val message: String? = null,
    @SerializedName("code") val code: Any? = null,
)