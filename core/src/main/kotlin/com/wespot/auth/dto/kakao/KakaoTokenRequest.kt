package com.wespot.auth.dto.kakao

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token-request-body 참고
 */
data class KakaoTokenRequest(
    @JsonProperty("grant_type")
    val grantType: String,
    @JsonProperty("client_id")
    val clientId: String,
    @JsonProperty("redirect_uri")
    val redirectUri: String,
    val code: String,
)
