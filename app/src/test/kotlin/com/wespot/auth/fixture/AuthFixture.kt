package com.wespot.auth.fixture

import com.wespot.auth.dto.AuthData
import com.wespot.auth.dto.request.SignUpRequest
import com.wespot.auth.dto.request.ConsentsRequest
import com.wespot.auth.dto.response.TokenResponse
import com.wespot.user.Gender
import com.wespot.user.User
import com.wespot.user.fixture.UserFixture

object AuthFixture {

    fun createSignUpRequest(): SignUpRequest {
        return SignUpRequest(
            signUpToken = "signUpToken",
            name = "Test User",
            gender = Gender.MALE,
            consents = ConsentsRequest(
                marketing = true
            ),
            schoolId = 1L,
            grade = 1,
            classNumber = 1
        )
    }

    fun createAuthData(): AuthData {
        return AuthData(
            email = "test@test.com",
            socialRefreshToken = "testRefreshToken",
            socialEmail = "test@social.com",
            fcmToken = "fcmToken"
        )
    }

    fun createTokenResponse(refreshTokenExpiredAt: String): TokenResponse {
        return TokenResponse(
            accessToken = "accessToken",
            refreshToken = "refreshToken",
            refreshTokenExpiredAt = refreshTokenExpiredAt
        )
    }

    fun createUser(): User {
        return UserFixture.createWithId(1)
    }
}
