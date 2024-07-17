package com.wespot.auth.service

import com.wespot.auth.dto.*
import com.wespot.auth.dto.request.*
import com.wespot.auth.dto.response.SignUpResponse
import com.wespot.auth.dto.response.SocialResponse
import com.wespot.auth.dto.response.TokenResponse
import com.wespot.auth.port.out.AuthDataPort
import com.wespot.auth.port.out.RefreshTokenPort
import com.wespot.auth.service.jwt.JwtTokenProvider
import com.wespot.school.port.out.SchoolPort
import com.wespot.user.*
import com.wespot.user.fixture.UserFixture
import com.wespot.user.port.out.ProfilePort
import com.wespot.user.port.out.UserConsentPort
import com.wespot.user.port.out.UserPort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.NoSuchElementException

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

    val authService = spyk(AuthService(
        userPort = userPort,
        refreshTokenPort = refreshTokenPort,
        userConsentPort = userConsentPort,
        profilePort = profilePort,
        schoolPort = schoolPort,
        authDataPort = authDataPort,
        jwtTokenProvider = jwtTokenProvider,
        socialAuthServiceFactory = socialAuthServiceFactory,
        authenticationService = authenticationService,
        authenticationManager = authenticationManager,
        passwordEncoder = passwordEncoder,
        refreshTokenService = refreshTokenService,
        secretKey = secretKey
    ))

    given("loginAccess 테스트") {

        val authLoginRequest = AuthLoginRequest(
            socialType = SocialType.KAKAO,
            authorizationCode = "authorizationCode",
            identityToken = "testIdentityToken",
            fcmToken = "testFcmToken"
        )
        val socialResponse = SocialResponse(
            socialId = "testSocialId",
            socialEmail = "testSocialEmail",
            socialRefreshToken = "testSocialRefreshToken"
        )

        val formatSocialEmail = "testSocialId@KAKAO"

        val authData = AuthData(
            email = formatSocialEmail,
            socialRefreshToken = socialResponse.socialRefreshToken,
            socialEmail = socialResponse.socialEmail,
        )

        val token = "testToken"

        every { authService.fetchSocialEmail(authLoginRequest) } returns socialResponse
        every { authService.formatSocialEmail(socialId = socialResponse.socialId, socialType = authLoginRequest.socialType) } returns formatSocialEmail
        every { userPort.findByEmail(formatSocialEmail) } returns null
        every { authDataPort.saveAuthData(token = any(), authData = authData) } just Runs
        every { authService.createSignUpToken(authData = authData) } returns token

        `when`("처음 서비스를 이용한 사용자가 loginAccess를 호출할 때") {

            val socialAccess = authService.socialAccess(authLoginRequest)

            then("fetchSocialEmail을 올바르게 가져온다") {
                socialResponse.socialId shouldBe "testSocialId"
                socialResponse.socialEmail shouldBe "testSocialEmail"
                socialResponse.socialRefreshToken shouldBe "testSocialRefreshToken"
            }

            then("socialEmail을 정해진 규격으로 만든다") {
                formatSocialEmail shouldBe "testSocialId@KAKAO"
            }

            then("AuthData를 올바르게 생성한다") {
                authData.email shouldBe "testSocialId@KAKAO"
                authData.socialRefreshToken shouldBe "testSocialRefreshToken"
                authData.socialEmail shouldBe "testSocialEmail"
            }

            then("createSignUpToken을 생성한다") {
                socialAccess shouldBe SignUpResponse(token)
            }
        }

        `when`("기존 사용자가 loginAccess를 호출할 때") {
            val user = UserFixture.createWithId(1)
            val tokenResponse = TokenResponse(
                accessToken = "accessToken",
                refreshToken = "refreshToken"
            )

            every { userPort.findByEmail(formatSocialEmail) } returns user
            every { authService.signIn(any()) } returns tokenResponse

            val socialAccess = authService.socialAccess(authLoginRequest)

            then("기존 사용자를 찾는다") {
                user.email shouldBe "TestEmail@Kakako"
                socialAccess shouldBe tokenResponse
            }

            then("signIn 메서드를 호출한다") {
                socialAccess shouldBe tokenResponse
            }
        }

        `when`("잘못된 socialType으로 loginAccess를 호출할 때") {
            every { authService.fetchSocialEmail(authLoginRequest) } throws NoSuchElementException("잘못된 socialType 입니다")

            then("NoSuchElementException이 발생해야 한다") {
                shouldThrow<NoSuchElementException> {
                    authService.socialAccess(authLoginRequest)
                }
            }
        }
    }

    given("signUp 테스트") {

        val signUpRequest = SignUpRequest(
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

        val authData = AuthData(
            email = "test@test.com",
            socialRefreshToken = "testRefreshToken",
            socialEmail = "test@social.com"
        )

        val user = UserFixture.createWithId(1)

        every { authService.checkSignUpToken(signUpRequest.signUpToken) } returns authData
        every { authService.createUser(authData, signUpRequest) } returns user
        every { userPort.save(any()) } returns user
        every { authService.saveRelatedEntities(user, signUpRequest) } just Runs
        every { authService.signIn(any()) } returns TokenResponse(
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )

        `when`("사용자가 signUp을 호출할 때") {
            val response = authService.signUp(signUpRequest)

            then("checkSignUpToken을 호출한다") {
                authService.checkSignUpToken(signUpRequest.signUpToken) shouldBe authData
            }

            then("createUser를 호출한다") {
                authService.createUser(authData, signUpRequest) shouldBe user
            }

            then("saveRelatedEntities를 호출한다") {
                authService.saveRelatedEntities(user, signUpRequest) shouldBe Unit
            }

            then("signIn을 호출한다") {
                val signInRequest = SignInRequest(
                    email = authData.email,
                    password = "${authData.email}$secretKey"
                )
                authService.signIn(signInRequest) shouldBe TokenResponse(
                    accessToken = "accessToken",
                    refreshToken = "refreshToken"
                )
            }

            then("TokenResponse를 반환한다") {
                response.accessToken shouldBe "accessToken"
                response.refreshToken shouldBe "refreshToken"
            }
        }

        `when`("잘못된 signUpToken으로 signUp을 호출할 때") {
            every { authService.checkSignUpToken(signUpRequest.signUpToken) } throws NoSuchElementException("잘못된 signUpToken 입니다")

            then("NoSuchElementException이 발생해야 한다") {
                shouldThrow<NoSuchElementException> {
                    authService.signUp(signUpRequest)
                }
            }
        }
    }

    given("signIn 테스트") {

        val signInRequest = SignInRequest(
            email = "test@test.com",
            password = "password"
        )

        val user = UserFixture.createWithId(1)
        val authentication = mockk<Authentication>()
        val generateToken = TokenResponse(
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )

        every { authenticationManager.authenticate(any()) } returns authentication
        every { authentication.name } returns user.email
        every { authService.getUserByEmail(user.email) } returns user
        every { jwtTokenProvider.generateToken(authentication) } returns generateToken
        every { refreshTokenService.saveOrUpdateRefreshToken(any(), any()) } just Runs

        `when`("사용자가 signIn을 호출할 때") {
            val response = authService.signIn(signInRequest)

            then("사용자 인증을 시도한다") {
                authenticationManager.authenticate(signInRequest.toAuthentication()) shouldBe authentication
            }

            then("사용자를 이메일로 찾는다") {
                authService.getUserByEmail(user.email) shouldBe user
            }

            then("토큰을 생성한다") {
                jwtTokenProvider.generateToken(authentication) shouldBe generateToken
            }

            then("refreshToken을 저장 또는 업데이트한다") {
                refreshTokenService.saveOrUpdateRefreshToken(generateToken.refreshToken, user) shouldBe Unit
            }

            then("TokenResponse를 반환한다") {
                response.accessToken shouldBe "accessToken"
                response.refreshToken shouldBe "refreshToken"
            }
        }
    }

    given("reIssueToken 테스트") {

        val refreshTokenRequest = RefreshTokenRequest(
            refreshToken = "testRefreshToken"
        )

        val user = UserFixture.createWithId(1)
        val authentication = mockk<Authentication>()
        val generateToken = TokenResponse(
            accessToken = "newAccessToken",
            refreshToken = "newRefreshToken"
        )

        every { authenticationService.getAuthentication(any()) } returns authentication
        every { authentication.name } returns user.email
        every { authService.getUserByEmail(user.email) } returns user
        every { jwtTokenProvider.generateToken(authentication) } returns generateToken
        every { refreshTokenService.saveOrUpdateRefreshToken(any(), any()) } just Runs

        `when`("사용자가 reIssueToken을 호출할 때") {
            val response = authService.reIssueToken(refreshTokenRequest)

            then("사용자 인증을 시도한다") {
                authenticationService.getAuthentication(refreshTokenRequest.refreshToken) shouldBe authentication
            }

            then("사용자를 이메일로 찾는다") {
                authService.getUserByEmail(user.email) shouldBe user
            }

            then("토큰을 생성한다") {
                jwtTokenProvider.generateToken(authentication) shouldBe generateToken
            }

            then("refreshToken을 저장 또는 업데이트한다") {
                refreshTokenService.saveOrUpdateRefreshToken(generateToken.refreshToken, user) shouldBe Unit
            }

            then("TokenResponse를 반환한다") {
                response.accessToken shouldBe "newAccessToken"
                response.refreshToken shouldBe "newRefreshToken"
            }
        }

        `when`("잘못된 refreshToken으로 reIssueToken을 호출할 때") {
            every { authenticationService.getAuthentication(refreshTokenRequest.refreshToken) } throws NoSuchElementException("잘못된 refreshToken 입니다")

            then("NoSuchElementException이 발생해야 한다") {
                shouldThrow<NoSuchElementException> {
                    authService.reIssueToken(refreshTokenRequest)
                }
            }
        }
    }

    given("revoke 테스트") {

        val user = UserFixture.createWithId(1)

        every { authService.getLoginUserId() } returns user.id
        every { userPort.findById(user.id) } returns user
        every { socialAuthServiceFactory.getService(user.social.socialType).revoke(user.social.socialId, user.social.socialRefreshToken) } returns true
        every { refreshTokenPort.deleteByUserId(user.id) } just Runs
        every { userPort.save(any()) } returns user

        `when`("사용자가 revoke를 호출할 때") {
            authService.revoke()

            then("로그인된 사용자를 찾는다") {
                authService.getLoginUserId() shouldBe user.id
            }

            then("사용자를 찾는다") {
                userPort.findById(user.id) shouldBe user
            }

            then("소셜 서비스의 revoke를 호출한다") {
                socialAuthServiceFactory.getService(user.social.socialType).revoke(user.social.socialId, user.social.socialRefreshToken) shouldBe true
            }

            then("refreshToken을 삭제한다") {
                refreshTokenPort.deleteByUserId(user.id) shouldBe Unit
            }

            then("사용자를 withdraw 상태로 저장한다") {
                userPort.save(user.withdraw()) shouldBe user
            }
        }

        `when`("잘못된 사용자 ID로 revoke를 호출할 때") {
            every { userPort.findById(user.id) } returns null

            then("NoSuchElementException이 발생해야 한다") {
                shouldThrow<NoSuchElementException> {
                    authService.revoke()
                }
            }
        }
    }
})
