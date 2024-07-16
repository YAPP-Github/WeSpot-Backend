package com.wespot.auth.service

import com.wespot.auth.dto.*
import com.wespot.auth.dto.request.*
import com.wespot.auth.dto.response.SignUpResponse
import com.wespot.auth.dto.response.SocialResponse
import com.wespot.auth.dto.response.TokenResponse
import com.wespot.auth.port.out.AuthDataPort
import com.wespot.auth.port.out.RefreshTokenPort
import com.wespot.auth.service.jwt.JwtTokenProvider
import com.wespot.auth.service.kakao.KakaoService
import com.wespot.school.School
import com.wespot.school.SchoolType
import com.wespot.school.port.out.SchoolPort
import com.wespot.user.*
import com.wespot.user.port.out.ProfilePort
import com.wespot.user.port.out.UserConsentPort
import com.wespot.user.port.out.UserPort
import io.jsonwebtoken.Claims
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

class AuthServiceTest : BehaviorSpec({

    val userPort = mockk<UserPort>()
    val refreshTokenPort = mockk<RefreshTokenPort>()
    val userConsentPort = mockk<UserConsentPort>()
    val profilePort = mockk<ProfilePort>()
    val schoolPort = mockk<SchoolPort>()
    val authDataPort = mockk<AuthDataPort>()
    val jwtTokenProvider = mockk<JwtTokenProvider>()
    val socialAuthServiceFactory = mockk<SocialAuthServiceFactory>()
    val authenticationService = mockk<AuthenticationService>()
    val authenticationManager = mockk<AuthenticationManager>()
    val passwordEncoder = mockk<PasswordEncoder>()
    val refreshTokenService = mockk<RefreshTokenService>()

    val secretKey = "testSecretKey"

    val authService = AuthService(
        userPort,
        refreshTokenPort,
        userConsentPort,
        profilePort,
        schoolPort,
        authDataPort,
        jwtTokenProvider,
        socialAuthServiceFactory,
        authenticationService,
        authenticationManager,
        passwordEncoder,
        refreshTokenService,
        secretKey
    )

    given("AuthService 테스트") {
        // TODO : 나중에 작성해서 PR 올리겠습니다!

    }
})
