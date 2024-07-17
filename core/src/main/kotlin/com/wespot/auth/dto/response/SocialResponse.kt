package com.wespot.auth.dto.response

data class SocialResponse(
    val socialId: String,
    val socialEmail: String,
    val socialRefreshToken: String
)
