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
        id: Long?,
    ) = User(
        id = id,
        name = "TestUser",
        schoolId = 1L,
        grade = 1,
        groupNumber = 1,
        setting = Setting(null, false),
        profile = Profile(null, "black", "image.png"),
        fcm = FCM(null, "token", LocalDateTime.now()),
        social = Social(SocialType.KAKAO, 1L, "refreshToken"),
        createdAt = LocalDateTime.now(),
        updatedAt = null,
        withdrawAt = LocalDateTime.now(),
    )

    fun createWithIdAndSettingAndProfileAndFCM(
        id: Long?,
        setting: Setting,
        profile: Profile,
        fcm: FCM
    ) = User(
        id = id,
        name = "TestUser",
        schoolId = 1,
        grade = 1,
        groupNumber = 1,
        setting = setting,
        profile = profile,
        fcm = fcm,
        social = Social(SocialType.KAKAO, 1L, "refreshToken"),
        createdAt = LocalDateTime.now(),
        updatedAt = null,
        withdrawAt = LocalDateTime.now(),
    )

}
