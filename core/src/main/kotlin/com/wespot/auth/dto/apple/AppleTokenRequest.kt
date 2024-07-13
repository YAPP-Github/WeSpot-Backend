package com.wespot.auth.dto.apple

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * https://developer.apple.com/documentation/sign_in_with_apple/generate_and_validate_tokens 참고
 */
data class AppleTokenRequest(
    @JsonProperty("client_id")
    val clientId: String,
    @JsonProperty("client_secret")
    val clientSecret: String,
    val code: String?,
    @JsonProperty("grant_type")
    val grantType: String,
    @JsonProperty("redirect_uri")
    val redirectUri: String?
)