package com.wespot.auth.dto.apple

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * https://developer.apple.com/documentation/sign_in_with_apple/revoke_tokens
 */
data class AppleRevokeRequest(
    @JsonProperty("client_id")
    val clientId: String,
    @JsonProperty("client_secret")
    val clientSecret: String,
    val token: String,
    @JsonProperty("token_type_hint")
    val tokenTypeHint: String
)
