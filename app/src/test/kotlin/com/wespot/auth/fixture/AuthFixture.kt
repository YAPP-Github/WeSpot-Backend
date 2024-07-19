package com.wespot.auth.fixture

import com.wespot.auth.dto.AuthData
import com.wespot.auth.dto.request.ProfileRequest
import com.wespot.auth.dto.request.SignUpRequest
import com.wespot.auth.dto.request.UserConsentRequest
import com.wespot.auth.dto.response.TokenResponse
import com.wespot.user.ConsentType
import com.wespot.user.User
import com.wespot.user.fixture.UserFixture

object AuthFixture {

    fun createSignUpRequest(): SignUpRequest {
        return SignUpRequest(
            signUpToken = "signUpToken",
            name = "Test User",
            introduction = "Hello, this is a test.",
            profile = ProfileRequest(
                backgroundColor = "blue",
                iconUrl = "iconUrl"
            ),
            userConsent = UserConsentRequest(
                consentType = ConsentType.MARKETING,
                consentValue = true
            ),
            schoolId = 1L,
            grade = 1,
            groupNumber = 1
        )
    }

    fun createAuthData(): AuthData {
        return AuthData(
            email = "test@test.com",
            socialRefreshToken = "testRefreshToken",
            socialEmail = "test@social.com"
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
