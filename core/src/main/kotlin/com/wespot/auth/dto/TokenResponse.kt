package com.wespot.auth.dto

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)