package com.wespot.auth.dto.response

data class TokenAndUserDetailResponse(
    val accessToken: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String,
    val setting: SettingResponse,
    val name: String,
    val isProfileChanged: Boolean
)
