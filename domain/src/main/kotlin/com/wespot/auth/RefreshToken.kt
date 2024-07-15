package com.wespot.auth

import com.wespot.user.User
import java.time.LocalDateTime

data class RefreshToken(
    val id : Long,
    val refreshToken: String,
    val user: User,
    val expiredAt: LocalDateTime?
)