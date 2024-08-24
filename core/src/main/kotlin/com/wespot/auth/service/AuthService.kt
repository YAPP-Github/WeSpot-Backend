package com.wespot.auth.service

import com.wespot.auth.dto.AuthData
import com.wespot.auth.dto.request.*
import com.wespot.auth.dto.response.SettingResponse
import com.wespot.auth.dto.response.SignUpResponse
import com.wespot.auth.dto.response.SocialResponse
import com.wespot.auth.dto.response.TokenAndUserDetailResponse
import com.wespot.auth.dto.response.TokenResponse
import com.wespot.auth.port.`in`.AuthUseCase
import com.wespot.auth.port.out.AuthDataPort
import com.wespot.auth.port.out.PersonalInfoPort
import com.wespot.auth.port.out.RefreshTokenPort
import com.wespot.auth.service.jwt.JwtTokenProvider
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.school.port.out.SchoolPort
import com.wespot.user.*
import com.wespot.user.event.CreatedVoteEvent
import com.wespot.user.event.SignUpUserEvent
import com.wespot.user.event.WelcomeMessageEvent
import com.wespot.user.port.out.ProfilePort
import com.wespot.user.port.out.RestrictionPort
import com.wespot.user.port.out.UserConsentPort
import com.wespot.user.port.out.UserPort
import com.wespot.user.port.out.FCMPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.HttpStatus
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
    private val restrictionPort: RestrictionPort,
    private val eventPublisher: ApplicationEventPublisher,
    private val fcmPort: FCMPort,
    private val personalInfoPort: PersonalInfoPort,

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
            fcmToken = authLoginRequest.fcmToken
        )
        val user = userPort.findByEmail(socialEmail)
            ?: return SignUpResponse(createSignUpToken(authData = authData))

        return signIn(createSignInRequest(user))
    }


    fun signIn(signInRequest: SignInRequest): TokenAndUserDetailResponse {
        val authentication = authenticateUser(signInRequest)
        val generateToken = jwtTokenProvider.generateToken(authentication)
        val user = getUserByEmail(authentication.name)
        checkWithdrawalStatus(user)

        refreshTokenService.saveOrUpdateRefreshToken(generateToken.refreshToken, user)

        return TokenAndUserDetailResponse(
            accessToken = generateToken.accessToken,
            refreshToken = generateToken.refreshToken,
            refreshTokenExpiredAt = generateToken.refreshTokenExpiredAt,
            setting = SettingResponse(
                isMessageNotification = false, // TODO : userConsent에 저장되어 있는 값으로 변경
                isVoteNotification = false, // TODO : userConsent에 저장되어 있는 값으로 변경
                isMarketingNotification = user.userConsent.consentValue ?: false
            ),
            name = user.name,
            isProfileChanged = true
        )
    }



    override fun signUp(signUpRequest: SignUpRequest): TokenAndUserDetailResponse {
        val signUpToken = checkSignUpToken(signUpRequest.signUpToken)

        val user = createUser(signUpToken, signUpRequest)
        val savedUser = userPort.save(user)
        eventPublisher.publishEvent(CreatedVoteEvent(savedUser))
        eventPublisher.publishEvent(SignUpUserEvent(savedUser))
        eventPublisher.publishEvent(WelcomeMessageEvent(savedUser))

        saveRelatedEntities(savedUser, signUpRequest, signUpToken.fcmToken)

        val signIn = signIn(createSignInRequest(savedUser))

        return TokenAndUserDetailResponse(
            accessToken = signIn.accessToken,
            refreshToken = signIn.refreshToken,
            refreshTokenExpiredAt = signIn.refreshTokenExpiredAt,
            setting = SettingResponse(
                isMessageNotification = false,
                isVoteNotification = false,
                isMarketingNotification = signUpRequest.consents.marketing
            ),
            name = signIn.name,
            isProfileChanged = false
        )
    }

    fun createUser(
        signUpToken: AuthData,
        signUpRequest: SignUpRequest
    ): User {
        val school = (schoolPort.findById(signUpRequest.schoolId)
            ?: throw CustomException(HttpStatus.NOT_FOUND, ExceptionView.TOAST, "해당 학교가 존재하지 않습니다."))

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

    fun saveRelatedEntities(
        user: User,
        signUpRequest: SignUpRequest,
        fcmToken: String?
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

        val fcm = FCM.from(fcmToken)
        val savedFcm = fcmPort.save(fcm)

        val updatedUser = User.update(
            user = user,
            userConsent = userConsent,
            profile = savedProfile,
            fcm = savedFcm,
            setting = null
        )
        updatedUser.changeSettings(
            isEnableVoteNotification = false,
            isEnableMessageNotification = false,
            isEnableMarketingNotification = signUpRequest.consents.marketing
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
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val withdrawUser = loginUser.withdraw()
        userPort.save(withdrawUser)
    }

    override fun adminLogin(adminLoginRequest: AdminLoginRequest): TokenResponse {

        val authentication = authenticationManager.authenticate(adminLoginRequest.toAuthentication())
        val generateToken = jwtTokenProvider.generateToken(authentication = authentication)
        val user = getUserByEmail(authentication.name)
        checkAdmin(user)

        refreshTokenService.saveOrUpdateRefreshToken(generateToken.refreshToken, user)

        return TokenResponse(
            accessToken = generateToken.accessToken,
            refreshToken = generateToken.refreshToken,
            refreshTokenExpiredAt = generateToken.refreshTokenExpiredAt
        )
    }

    private fun checkAdmin(adminUser: User) {
        if (adminUser.role != Role.ADMIN) {
            throw CustomException(HttpStatus.FORBIDDEN, ExceptionView.TOAST, "관리자만 접근 가능합니다.")
        }
    }

    fun fetchSocialEmail(authLoginRequest: AuthLoginRequest): SocialResponse {
        val fetchAuthToken = socialAuthServiceFactory.getService(authLoginRequest.socialType)
            .fetchAuthToken(authLoginRequest)

        val personalInfo = personalInfoPort.findBySocialId(fetchAuthToken.socialId)

        personalInfo?.let {
            restrictionPort.findById(it.restriction)?.let { restriction ->
                val isPermBanMessage = restriction.messageRestriction.restrictionType == RestrictionType.PERMANENT_BAN_MESSAGE_REPORT
                val isPermBanVote = restriction.voteRestriction.restrictionType == RestrictionType.PERMANENT_BAN_VOTE_REPORT

                require(!(isPermBanMessage || isPermBanVote)) { "영구 제한된 계정입니다." }
            }
        }

        return SocialResponse(
            socialId = fetchAuthToken.socialId,
            socialEmail = fetchAuthToken.socialEmail,
            socialRefreshToken = fetchAuthToken.socialRefreshToken
        )
    }

    fun getUserByEmail(email: String): User {
        return userPort.findByEmail(email)
            ?: throw CustomException(HttpStatus.NOT_FOUND, ExceptionView.TOAST, "유저를 찾을 수 없습니다.")
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
        return authDataPort.getAuthData(token) ?: throw CustomException(
            HttpStatus.FORBIDDEN,
            ExceptionView.TOAST,
            "회원가입 토큰이 만료되었습니다."
        )
    }

    fun getLoginUserId(): Long {
        return SecurityUtils.getLoginUserId(userPort)
    }

    fun checkWithdrawalStatus(user: User) {
        if (user.withdrawalStatus == WithdrawalStatus.ACTIVE || user.withdrawalCompleteAt?.isAfter(LocalDateTime.now()) == true) {
            val cancelWithdraw = user.cancelWithdraw()
            userPort.save(cancelWithdraw)
        }
    }

}
