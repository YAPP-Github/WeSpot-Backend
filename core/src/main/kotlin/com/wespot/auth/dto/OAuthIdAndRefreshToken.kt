package com.wespot.auth.dto

data class OAuthIdAndRefreshToken(
    val oAuthId: String,
    val refreshToken: String
)
