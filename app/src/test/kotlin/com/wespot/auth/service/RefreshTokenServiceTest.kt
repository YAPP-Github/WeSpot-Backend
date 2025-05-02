package com.wespot.auth.service

import com.wespot.auth.JwtTokenInfo.REFRESH_TOKEN_EXPIRY_DAYS
import com.wespot.auth.RefreshToken
import com.wespot.auth.fixture.RefreshTokenFixture
import com.wespot.auth.port.out.RefreshTokenPort
import com.wespot.user.fixture.UserFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import java.time.LocalDateTime

class RefreshTokenServiceTest : BehaviorSpec({
    val refreshTokenPort = mockk<RefreshTokenPort>()
    val refreshTokenService = RefreshTokenService(refreshTokenPort)

    given("refreshTokenService 테스트") {
        val token = "newRefreshToken"
        val user = UserFixture.createWithIdSchool(1)

        val now = LocalDateTime.now()
        mockkStatic(LocalDateTime::class)
        every { LocalDateTime.now() } returns now

        val existingToken = RefreshToken.create(
            refreshToken = "oldToken",
            user = user,
            createdAt = LocalDateTime.now().minusDays(1),
            updatedAt = LocalDateTime.now().minusDays(1),
            expiredAt = LocalDateTime.now().minusDays(1)
        )

        val updatedToken = RefreshToken.update(
            id = existingToken.id,
            refreshToken = token,
            user = existingToken.user,
            createdAt = existingToken.createdAt,
            updatedAt = LocalDateTime.now(),
            expiredAt = LocalDateTime.now().plusDays(REFRESH_TOKEN_EXPIRY_DAYS)
        )

        val existingRefreshToken = RefreshTokenFixture.createWithIdAndRefreshToken(1, existingToken)
        val updatedRefreshToken = RefreshTokenFixture.createWithIdAndRefreshToken(1, updatedToken)


        `when`("기존 리프레시 토큰이 있을 때") {

            every { refreshTokenPort.findByUserId(user.id) } returns existingToken
            every { refreshTokenPort.saveOrUpdate(any()) } returns updatedRefreshToken

            then("리프레시 토큰이 업데이트되어야 한다") {
                refreshTokenService.saveOrUpdateRefreshToken(token, user)
                val saveOrUpdateToken = refreshTokenPort.saveOrUpdate(updatedToken)

                saveOrUpdateToken.refreshToken shouldBe updatedToken.refreshToken
                saveOrUpdateToken.expiredAt shouldBe updatedToken.expiredAt

            }
        }

        `when`("기존 리프레시 토큰이 없을 때") {
            every { refreshTokenPort.findByUserId(user.id) } returns null
            every { refreshTokenPort.saveOrUpdate(any()) } returns existingRefreshToken

            then("새로운 리프레시 토큰이 생성되어야 한다") {
                refreshTokenService.saveOrUpdateRefreshToken(token, user)

                val newToken = RefreshToken.create(
                    refreshToken = token,
                    user = user,
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now(),
                    expiredAt = LocalDateTime.now().plusDays(REFRESH_TOKEN_EXPIRY_DAYS)
                )

                verify { refreshTokenPort.saveOrUpdate(newToken) }
            }
        }
    }
})
