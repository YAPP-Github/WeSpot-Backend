package com.wespot.auth.fixture

import com.wespot.auth.RefreshToken
import com.wespot.user.*
import com.wespot.user.fixture.UserFixture
import java.time.LocalDateTime

object RefreshTokenFixture {

    fun createWithIdAndRefreshToken(
        id: Long,
        refreshToken: RefreshToken
    ) = RefreshToken(
        id = id,
        refreshToken = refreshToken.refreshToken,
        user = refreshToken.user,
        createdAt = refreshToken.createdAt,
        updatedAt = refreshToken.updatedAt,
        expiredAt = refreshToken.expiredAt
    )

}
