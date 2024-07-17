package com.wespot.user.fixture

import com.wespot.user.*
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
        schoolId = 1L,
        grade = 1,
        groupNumber = 1,
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

}
