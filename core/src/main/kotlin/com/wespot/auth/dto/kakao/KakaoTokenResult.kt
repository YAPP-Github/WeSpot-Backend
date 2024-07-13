package com.wespot.auth.dto.kakao

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token-response 참고
 */
data class KakaoTokenResult(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("token_type")
    val tokenType: String,
    @JsonProperty("refresh_token")
    val refreshToken: String?,
    @JsonProperty("refresh_token_expires_in")
    val refreshTokenExpiresIn: String?,
)
