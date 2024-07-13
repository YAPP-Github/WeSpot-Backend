package com.wespot.auth.dto.apple

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * https://developer.apple.com/documentation/sign_in_with_apple/generate_and_validate_tokens 참고
 */
data class AppleTokenResult(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("token_type")
    val tokenType: String,
    @JsonProperty("expires_in")
    val expiresIn: Int,
    @JsonProperty("refresh_token")
    val refreshToken: String?,
    @JsonProperty("id_token")
    val idToken: String,
    val error: String?,
)
