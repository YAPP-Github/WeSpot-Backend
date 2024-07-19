package com.wespot.auth.dto.response

import java.time.LocalDateTime
import java.util.*

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String
)
