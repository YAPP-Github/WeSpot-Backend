package com.wespot.auth.service

import com.wespot.auth.dto.AuthData
import com.wespot.auth.dto.request.AuthLoginRequest
import com.wespot.auth.dto.request.RefreshTokenRequest
import com.wespot.auth.dto.request.SignInRequest
import com.wespot.auth.dto.request.SignUpRequest
import com.wespot.auth.dto.response.SignUpResponse
import com.wespot.auth.dto.response.SocialResponse
import com.wespot.auth.dto.response.TokenResponse
import com.wespot.auth.port.`in`.AuthUseCase
import com.wespot.auth.port.out.AuthDataPort
import com.wespot.auth.port.out.RefreshTokenPort
import com.wespot.auth.service.jwt.JwtTokenProvider
import com.wespot.school.port.out.SchoolPort
import com.wespot.user.ConsentType
import com.wespot.user.Profile
import com.wespot.user.Social
import com.wespot.user.SocialType
import com.wespot.user.User
import com.wespot.user.UserConsent
import com.wespot.user.event.VoteCreateEvent
import com.wespot.user.port.out.ProfilePort
import com.wespot.user.port.out.UserConsentPort
import com.wespot.user.port.out.UserPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
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
    private val eventPublisher: ApplicationEventPublisher,

    @Value("\${jwt.secret}")
    private val secretKey: String
) : AuthUseCase {


    override fun socialAccess(authLoginRequest: AuthLoginRequest): Any {
        val socialResponse = fetchSocialEmail(authLoginRequest)
        val socialEmail = formatSocialEmail(socialResponse.socialId, authLoginRequest.socialType)

        val authData = AuthData(
            email = socialEmail,
            socialRefreshToken = socialResponse.socialRefreshToken,
            socialEmail = socialResponse.socialEmail,
        )
        val user = userPort.findByEmail(socialEmail)
            ?: return SignUpResponse(createSignUpToken(authData = authData))

        return signIn(createSignInRequest(user))
    }


    fun signIn(signInRequest: SignInRequest): TokenResponse {
        val authentication = authenticateUser(signInRequest)
        val generateToken = jwtTokenProvider.generateToken(authentication)
        val user = getUserByEmail(authentication.name)

        refreshTokenService.saveOrUpdateRefreshToken(generateToken.refreshToken, user)

        return TokenResponse(
            accessToken = generateToken.accessToken,
            refreshToken = generateToken.refreshToken,
            refreshTokenExpiredAt = generateToken.refreshTokenExpiredAt
        )
    }


    override fun signUp(signUpRequest: SignUpRequest): TokenResponse {
        val signUpToken = checkSignUpToken(signUpRequest.signUpToken)

        val user = createUser(signUpToken, signUpRequest)
        val savedUser = userPort.save(user)
        createVoteIfRegisterFirstForClass(user)

        saveRelatedEntities(savedUser, signUpRequest)

        return signIn(createSignInRequest(savedUser))
    }

    fun createUser(
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
            name = signUpRequest.name,
            grade = signUpRequest.grade,
            groupNumber = signUpRequest.classNumber,
            social = social,
            gender = signUpRequest.gender
        )
    }

    fun createVoteIfRegisterFirstForClass(user: User) {
        if (userPort.existsBySchoolIdAndGradeAndClassNumber(user.schoolId, user.grade, user.classNumber)) {
            return
        }

        eventPublisher.publishEvent(VoteCreateEvent(user))
    }

    fun saveRelatedEntities(
        user: User,
        signUpRequest: SignUpRequest
    ) {

        var userConsent: UserConsent? = null
        if (signUpRequest.consents.marketing) {
            val marketingConsent = UserConsent.create(
                consentType = ConsentType.MARKETING,
                consentValue = true,
                consentedAt = LocalDateTime.now()
            )
            userConsent = userConsentPort.save(marketingConsent)
        }

        val profile = Profile.createInit()
        val savedProfile = profilePort.save(profile)

        val updatedUser = User.update(
            user = user,
            userConsent = userConsent,
            profile = savedProfile,
            fcm = null,
            setting = null
        )
        userPort.save(updatedUser)
    }

    override fun reIssueToken(refreshTokenRequest: RefreshTokenRequest): TokenResponse {
        val authentication = authenticationService.getAuthentication(token = refreshTokenRequest.refreshToken)
        val generateToken = jwtTokenProvider.generateToken(authentication = authentication)
        val user = getUserByEmail(authentication.name)

        refreshTokenService.saveOrUpdateRefreshToken(generateToken.refreshToken, user)

        return TokenResponse(
            accessToken = generateToken.accessToken,
            refreshToken = generateToken.refreshToken,
            refreshTokenExpiredAt = generateToken.refreshTokenExpiredAt
        )
    }

    override fun revoke() {
        val loginUserId = getLoginUserId()
        val revokeUser = userPort.findById(loginUserId)
            ?: throw NoSuchElementException("해당 계정이 존재하지 않습니다.")

        socialAuthServiceFactory.getService(revokeUser.social.socialType)
            .revoke(revokeUser.social.socialId, revokeUser.social.socialRefreshToken)

        refreshTokenPort.deleteByUserId(loginUserId)
        userPort.save(revokeUser.withdraw())
    }

    fun fetchSocialEmail(authLoginRequest: AuthLoginRequest): SocialResponse {
        return socialAuthServiceFactory.getService(authLoginRequest.socialType)
            .fetchAuthToken(authLoginRequest)
    }


    fun getUserByEmail(email: String): User {
        return userPort.findByEmail(email)
            ?: throw NoSuchElementException("유저를 찾을 수 없습니다.")
    }

    private fun authenticateUser(signInRequest: SignInRequest) =
        authenticationManager.authenticate(signInRequest.toAuthentication())

    private fun createSignInRequest(user: User) = SignInRequest(
        email = user.email,
        password = "${user.email}$secretKey"
    )

    fun formatSocialEmail(socialId: String, socialType: SocialType): String {
        return "$socialId@${socialType.name}"
    }


    fun createSignUpToken(authData: AuthData): String {
        val token = UUID.randomUUID().toString()
        authDataPort.saveAuthData(token, authData)

        return token
    }

    fun checkSignUpToken(token: String): AuthData {
        return authDataPort.getAuthData(token) ?: throw NoSuchElementException("회원가입 토큰이 만료되었습니다.")
    }

    fun getLoginUserId(): Long {
        return SecurityUtils.getLoginUserId(userPort)
    }

}
