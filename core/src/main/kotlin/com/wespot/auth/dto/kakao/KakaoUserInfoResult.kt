package com.wespot.auth.dto.kakao

/**
 * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info-response 참고
 */
data class KakaoUserInfoResult(
    val id: Long,
    val connected_at: String,
)
