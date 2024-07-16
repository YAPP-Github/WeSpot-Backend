package com.wespot.auth.service

import com.wespot.auth.dto.*
import com.wespot.auth.dto.request.AuthLoginRequest
import com.wespot.auth.dto.request.RefreshTokenRequest
import com.wespot.auth.dto.request.SignInRequest
import com.wespot.auth.dto.request.SignUpRequest
import com.wespot.auth.dto.response.SignUpResponse
import com.wespot.auth.dto.response.SocialResponse
import com.wespot.auth.dto.response.TokenResponse
import com.wespot.auth.port.out.AuthDataPort
import com.wespot.auth.port.out.RefreshTokenPort
import com.wespot.auth.service.jwt.JwtTokenProvider
import com.wespot.school.port.out.SchoolPort
import com.wespot.user.*
import com.wespot.user.port.out.ProfilePort
import com.wespot.user.port.out.UserConsentPort
import com.wespot.user.port.out.UserPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional
class AuthService(
    private val userPort: UserPort,
    private val refreshTokenPort: RefreshTokenPort,
    private val userConsentPort: UserConsentPort,
    private val profilePort: ProfilePort,
    private val schoolPort: SchoolPort,
    private val authDataPort: AuthDataPort,
    private val jwtTokenProvider: JwtTokenProvider,
    private val socialAuthServiceFactory: SocialAuthServiceFactory,
    private val authenticationService: AuthenticationService,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder,
    private val refreshTokenService: RefreshTokenService,

    @Value("\${jwt.secret}")
    private val secretKey: String
) {


    fun socialAccess(authLoginRequest: AuthLoginRequest): Any {

        val socialResponse = fetchSocialEmail(authLoginRequest)
        val socialEmail = formatSocialEmail(socialResponse.socialId, authLoginRequest.socialType)

        val authData = AuthData(
            email = socialEmail,
            socialRefreshToken = socialResponse.socialRefreshToken,
            socialEmail = socialResponse.socialEmail,
        )
        val user = userPort.findByEmail(socialEmail)
            ?: return SignUpResponse(createSignUpToken(authDate = authData))

        return signIn(createSignInRequest(user))

    }


    fun signIn(signInRequest: SignInRequest): TokenResponse {

        val authentication = authenticateUser(signInRequest)
        val generateToken = jwtTokenProvider.generateToken(authentication)
        val user = getUserByEmail(authentication.name)

        refreshTokenService.saveOrUpdateRefreshToken(generateToken.refreshToken, user)

        return TokenResponse(
            accessToken = generateToken.accessToken,
            refreshToken = generateToken.refreshToken
        )

    }


    fun signUp(signUpRequest: SignUpRequest): TokenResponse {

        val signUpToken = checkSignUpToken(signUpRequest.signUpToken)

        val user = createUser(signUpToken, signUpRequest)
        val savedUser = userPort.save(user)

        saveRelatedEntities(savedUser, signUpRequest)

        return signIn(createSignInRequest(savedUser))

    }

    private fun createUser(
        signUpToken: AuthData,
        signUpRequest: SignUpRequest
    ): User {

        val school = (schoolPort.findById(signUpRequest.schoolId)
            ?: throw NoSuchElementException("해당 학교가 존재하지 않습니다."))

        val social = Social.create(
            email = signUpToken.email,
            socialEmail = signUpToken.socialEmail,
            socialRefreshToken = signUpToken.socialRefreshToken
        )

        return User.create(
            email = signUpToken.email,
            password = passwordEncoder.encode(signUpToken.email + secretKey),
            schoolId = school.id,
            grade = signUpRequest.grade,
            groupNumber = signUpRequest.groupNumber,
            social = social
        )

    }

    fun saveRelatedEntities(
        user: User,
        signUpRequest: SignUpRequest
    ) {

        val userConsent = UserConsent.create(
            user = user,
            consentType = signUpRequest.userConsent.consentType,
            consentValue = signUpRequest.userConsent.consentValue,
            consentedAt = LocalDateTime.now()
        )

        val profile = Profile.create(
            user = user,
            backgroundColor = signUpRequest.profile.backgroundColor,
            iconUrl = signUpRequest.profile.iconUrl
        )

        userConsentPort.save(userConsent)
        profilePort.save(profile)
    }

    fun reIssueToken(refreshTokenRequest: RefreshTokenRequest): TokenResponse {

        val authentication = authenticationService.getAuthentication(token = refreshTokenRequest.refreshToken)
        val generateToken = jwtTokenProvider.generateToken(authentication = authentication)
        val user = getUserByEmail(authentication.name)

        refreshTokenService.saveOrUpdateRefreshToken(generateToken.refreshToken, user)

        return TokenResponse(
            accessToken = generateToken.accessToken,
            refreshToken = generateToken.refreshToken
        )

    }

    fun revoke() {

        val loginUserId = getLoginUserId()
        val revokeUser = userPort.findById(loginUserId)
            ?: throw NoSuchElementException("해당 계정이 존재하지 않습니다.")

        socialAuthServiceFactory.getService(revokeUser.social.socialType)
            .revoke(revokeUser.social.socialId, revokeUser.social.socialRefreshToken)

        refreshTokenPort.deleteByUserId(loginUserId)
        userPort.save(revokeUser.withdraw(revokeUser))

    }

    private fun fetchSocialEmail(authLoginRequest: AuthLoginRequest): SocialResponse {
        return socialAuthServiceFactory.getService(authLoginRequest.socialType)
            .fetchAuthToken(authLoginRequest)
    }


    private fun getUserByEmail(email: String): User {
        return userPort.findByEmail(email)
            ?: throw NoSuchElementException("유저를 찾을 수 없습니다.")
    }

    private fun authenticateUser(signInRequest: SignInRequest) =
        authenticationManager.authenticate(signInRequest.toAuthentication())

    private fun createSignInRequest(user: User) = SignInRequest(
        email = user.email,
        password = "${user.email}$secretKey"
    )

    private fun formatSocialEmail(socialId: String, socialType: SocialType): String {
        return "$socialId@${socialType.name}"
    }


    private fun createSignUpToken(authDate: AuthData): String {

        val token = UUID.randomUUID().toString()
        authDataPort.saveAuthData(token, authDate)

        return token
    }

    fun checkSignUpToken(token: String): AuthData {
        return authDataPort.getAuthData(token) ?: throw NoSuchElementException("회원가입 토큰이 만료되었습니다.")
    }

    fun getLoginUserId(): Long {
        return SecurityUtils.getLoginUserId(userPort)
    }

}
