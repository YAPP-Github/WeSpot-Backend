package com.wespot.auth.service

import com.wespot.auth.JwtTokenInfo.REFRESH_TOKEN_EXPIRY_DAYS
import com.wespot.auth.RefreshToken
import com.wespot.auth.port.out.RefreshTokenPort
import com.wespot.user.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class RefreshTokenService(
    private val refreshTokenPort: RefreshTokenPort
) {
    @Transactional
    fun saveOrUpdateRefreshToken(token: String, user: User) {
        val findRefreshToken = refreshTokenPort.findByUserId(user.id)
        val refreshToken: RefreshToken

        if (findRefreshToken != null) {
            refreshToken = RefreshToken.update(
                id = findRefreshToken.id,
                refreshToken = token,
                user = findRefreshToken.user,
                createdAt = findRefreshToken.createdAt ?: LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                expiredAt = LocalDateTime.now().plusDays(REFRESH_TOKEN_EXPIRY_DAYS)
            )
        } else {
            refreshToken = RefreshToken.create(
                refreshToken = token,
                user = user,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                expiredAt = LocalDateTime.now().plusDays(REFRESH_TOKEN_EXPIRY_DAYS)
            )
        }
        refreshTokenPort.saveOrUpdate(refreshToken)
    }
}
