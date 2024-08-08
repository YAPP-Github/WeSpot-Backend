package com.wespot.auth.dto.response


data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String
)
