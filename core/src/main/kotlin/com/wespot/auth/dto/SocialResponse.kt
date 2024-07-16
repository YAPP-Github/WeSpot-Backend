package com.wespot.auth.dto

data class SocialResponse(
    val socialId: String,
    val socialEmail: String,
    val socialRefreshToken: String
)
