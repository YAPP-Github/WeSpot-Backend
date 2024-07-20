package com.wespot.user.fixture

import com.wespot.auth.PrincipalDetails
import com.wespot.auth.dto.request.ProfileRequest
import com.wespot.user.*
import com.wespot.user.dto.request.UpdateProfileRequest
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.time.LocalDateTime

object UserFixture {

    fun createWithId(
        id: Long,
    ) = User(
        id = id,
        email = "TestEmail@Kakako",
        password = "TestPassword",
        role = Role.USER,
        name = "TestUser",
        introduction = "hello",
        gender = "male",
        schoolId = 1L,
        grade = 1,
        classNumber = 1,
        setting = Setting(),
        profile = Profile(0, "black", "image.png"),
        fcm = FCM(0, "token", LocalDateTime.now()),
        social = Social(
            socialType = SocialType.KAKAO,
            socialId = "1123123",
            socialEmail = null,
            socialRefreshToken = "refreshToken"
        ),
        userConsent = UserConsent(
            id = 0,
            consentType = ConsentType.MARKETING,
            consentValue = true,
            consentedAt = LocalDateTime.now()
        ),
        createdAt = LocalDateTime.now(),
        updatedAt = null,
        withdrawAt = LocalDateTime.now(),
    )

    // SecurityContextHolder를 사용하여 테스트를 위한 User를 설정
    fun setSecurityContextUser(user : User){
        val mockUserDetail = PrincipalDetails(user)

        val authentication = TestingAuthenticationToken(mockUserDetail, null)
        SecurityContextHolder.getContext().authentication = authentication
    }

    fun updateProfileRequest(
        introduction: String = "hello",
        profile: Profile = Profile(0, "black", "image.png"),
    ) = UpdateProfileRequest(
        introduction = introduction,
        profile = ProfileRequest(
            backgroundColor = profile.backgroundColor,
            iconUrl = profile.iconUrl
        )
    )
}
