package com.wespot.user.fixture

import com.wespot.user.FCM
import com.wespot.user.Profile
import com.wespot.user.Setting
import com.wespot.user.Social
import com.wespot.user.SocialType
import com.wespot.user.User
import java.time.LocalDateTime

object UserFixture {

    fun createWithId(
        id: Long,
    ) = User(
        id = id,
        name = "TestUser",
        schoolId = 1L,
        grade = 1,
        groupNumber = 1,
        setting = Setting(0, false),
        profile = Profile(0, "black", "image.png"),
        fcm = FCM(0, "token", LocalDateTime.now()),
        social = Social(SocialType.KAKAO, 1L, "refreshToken"),
        createdAt = LocalDateTime.now(),
        updatedAt = null,
        withdrawAt = LocalDateTime.now(),
    )

}
