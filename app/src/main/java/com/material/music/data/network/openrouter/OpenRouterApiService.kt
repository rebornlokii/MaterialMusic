package com.material.music.data.network.openrouter

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Retrofit interface for OpenRouter's chat completions API.
 *
 * Used only to restate a request in the vocabulary the offline engine understands. The library is
 * never sent, and no song is ever named by the service: it returns a short piece of text, which
 * the local parser then scores against the local index exactly like a typed request.
 */
interface OpenRouterApiService {

    /**
     * [authorization] is passed per call rather than as an interceptor, so the key cannot be
     * attached to a request to any other host. A blank key is never sent at all — the caller
     * checks before it gets here.
     */
    @POST("api/v1/chat/completions")
    suspend fun chatCompletions(
        @Header("Authorization") authorization: String,
        @Body request: OpenRouterChatRequest,
    ): OpenRouterChatResponse
}