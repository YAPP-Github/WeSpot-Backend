package com.wespot.auth

import com.wespot.user.User
import java.time.LocalDateTime

data class RefreshToken(
    val id : Long,
    val refreshToken: String,
    val user: User,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val expiredAt: LocalDateTime
){
    companion object {
        fun create(
            refreshToken: String,
            user: User,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime,
            expiredAt: LocalDateTime
        ) =
            RefreshToken(
                id = 0L,
                refreshToken = refreshToken,
                user = user,
                createdAt = createdAt,
                updatedAt = updatedAt,
                expiredAt = expiredAt
            )

        fun update(
            id: Long,
            refreshToken: String,
            user: User,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime,
            expiredAt: LocalDateTime
        ) =
            RefreshToken(
                id = id,
                refreshToken = refreshToken,
                user = user,
                createdAt = createdAt,
                updatedAt = updatedAt,
                expiredAt = expiredAt
        )
    }
}
