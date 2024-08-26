package com.wespot.user.fixture

import com.wespot.auth.PrincipalDetails
import com.wespot.auth.dto.request.ProfileRequest
import com.wespot.user.restriction.Restriction
import com.wespot.user.Role
import com.wespot.user.Setting
import com.wespot.user.Social
import com.wespot.user.SocialType
import com.wespot.user.User
import com.wespot.user.UserConsent
import com.wespot.user.UserIntroduction
import com.wespot.user.Gender
import com.wespot.user.Profile
import com.wespot.user.FCM
import com.wespot.user.WithdrawalStatus
import com.wespot.user.ConsentType
import com.wespot.user.RestrictionType
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
        introduction = UserIntroduction.from("hello"),
        gender = Gender.MALE,
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
        restriction = Restriction.createInitialState(),
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        withdrawalStatus = WithdrawalStatus.NONE,
        withdrawalRequestAt = null,
        withdrawalCancelAt = null,
        withdrawalCompleteAt = null
    )

    fun createWithIdAndEmail(
        id: Long,
        email: String,
    ) = User(
        id = id,
        email = email,
        password = "TestPassword",
        role = Role.USER,
        name = "TestUser",
        introduction = UserIntroduction.from("hello"),
        gender = Gender.MALE,
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
        restriction = Restriction.createInitialState(),
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        withdrawalStatus = WithdrawalStatus.NONE,
        withdrawalRequestAt = null,
        withdrawalCancelAt = null,
        withdrawalCompleteAt = null
    )

    fun createSender(
        id: Long = 1L
    ) = User(
        id = id,
        email = "sender@example.com",
        password = "password",
        role = Role.USER,
        name = "Sender",
        introduction = UserIntroduction.from("intro"),
        gender = Gender.MALE,
        schoolId = 1L,
        grade = 1,
        classNumber = 1,
        setting = Setting(),
        profile = Profile(1L, "black", "image.png"),
        fcm = null,
        social = Social(
            socialType = SocialType.KAKAO,
            socialId = "1123123",
            socialEmail = null,
            socialRefreshToken = "refreshToken"
        ),
        userConsent = UserConsent(
            id = 1,
            consentType = ConsentType.MARKETING,
            consentValue = true,
            consentedAt = LocalDateTime.now()
        ),
        restriction = Restriction.createInitialState(),
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        withdrawalStatus = WithdrawalStatus.NONE,
        withdrawalRequestAt = null,
        withdrawalCancelAt = null,
        withdrawalCompleteAt = null
    )

    fun createReceiver(
        id: Long = 2L
    ) = User(
        id = id,
        email = "receiver@example.com",
        password = "password",
        role = Role.USER,
        name = "Receiver",
        introduction = UserIntroduction.from("intro"),
        gender = Gender.MALE,
        schoolId = 1L,
        grade = 1,
        classNumber = 1,
        setting = Setting(),
        profile = Profile(2L, "black", "image.png"),
        fcm = null,
        social = Social(
            socialType = SocialType.KAKAO,
            socialId = "1123123",
            socialEmail = null,
            socialRefreshToken = "refreshToken"
        ),
        userConsent = UserConsent(
            id = 2,
            consentType = ConsentType.MARKETING,
            consentValue = true,
            consentedAt = LocalDateTime.now()
        ),
        restriction = Restriction.createInitialState(),
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        withdrawalStatus = WithdrawalStatus.NONE,
        withdrawalRequestAt = null,
        withdrawalCancelAt = null,
        withdrawalCompleteAt = null
    )

    // SecurityContextHolder를 사용하여 테스트를 위한 User를 설정
    fun setSecurityContextUser(user: User) {
        val mockUserDetail = PrincipalDetails(user)

        val authentication = TestingAuthenticationToken(mockUserDetail, null)
        SecurityContextHolder.getContext().authentication = authentication
    }

    fun updateProfileRequest(
        introduction: String = "hello",
        profile: Profile = Profile(0, "black", "image.png"),
    ) = UpdateProfileRequest(
        introduction = introduction,
        backgroundColor = profile.backgroundColor,
        iconUrl = profile.iconUrl
    )

    fun createUser(
        id: Long,
        email: String,
        name: String,
        schoolId: Long,
        profileId: Long = id
    ): User {
        return User(
            id = id,
            email = email,
            password = "password",
            name = name,
            introduction = UserIntroduction.from("소개 $id"),
            gender = Gender.MALE,
            schoolId = schoolId,
            profile = Profile(profileId, "", ""),
            fcm = null,
            userConsent = UserConsent(
                id = 0,
                consentType = ConsentType.MARKETING,
                consentValue = true,
                consentedAt = LocalDateTime.now()
            ),
            grade = 1,
            classNumber = 1,
            setting = Setting(),
            social = Social(
                socialType = SocialType.KAKAO,
                socialId = "1234",
                socialEmail = null,
                socialRefreshToken = ""
            ),
            role = Role.USER,
            restriction = Restriction.createInitialState(),
            withdrawalStatus = WithdrawalStatus.NONE,
            withdrawalRequestAt = null,
            withdrawalCancelAt = null,
            withdrawalCompleteAt = null,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    fun createWithSchoolIdAndGradeAndClassNumber(
        schoolId: Long,
        grade: Int,
        classNumber: Int
    ) = User(
        id = 0L,
        email = "TestEmail@Kakako",
        password = "TestPassword",
        role = Role.USER,
        name = "TestUser",
        introduction = UserIntroduction.from("hello"),
        gender = Gender.MALE,
        schoolId = schoolId,
        grade = grade,
        classNumber = classNumber,
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
        restriction = Restriction.createInitialState(),
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        withdrawalStatus = WithdrawalStatus.NONE,
        withdrawalRequestAt = null,
        withdrawalCancelAt = null,
        withdrawalCompleteAt = null
    )

    fun createWithIdAndSchoolIdAndGradeAndClassNumber(
        id: Long,
        schoolId: Long,
        grade: Int,
        classNumber: Int
    ) = User(
        id = id,
        email = "TestEmail@Kakako",
        password = "TestPassword",
        role = Role.USER,
        name = "TestUser",
        introduction = UserIntroduction.from("hello"),
        gender = Gender.MALE,
        schoolId = schoolId,
        grade = grade,
        classNumber = classNumber,
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
        restriction = Restriction.createInitialState(),
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        withdrawalStatus = WithdrawalStatus.NONE,
        withdrawalRequestAt = null,
        withdrawalCancelAt = null,
        withdrawalCompleteAt = null
    )

    fun createWithEmailAndSchoolIdAndGradeAndClassNumber(
        email: String,
        schoolId: Long,
        grade: Int,
        classNumber: Int
    ) = User(
        id = 0L,
        email = email,
        password = "TestPassword",
        role = Role.USER,
        name = "TestUser",
        introduction = UserIntroduction.from("hello"),
        gender = Gender.MALE,
        schoolId = schoolId,
        grade = grade,
        classNumber = classNumber,
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
        restriction = Restriction.createInitialState(),
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        withdrawalStatus = WithdrawalStatus.NONE,
        withdrawalRequestAt = null,
        withdrawalCancelAt = null,
        withdrawalCompleteAt = null
    )

    fun createUserWithRestrictionTypeAndRestrictDay(restrictions: List<Pair<RestrictionType, Long>>): User {
        var restriction = Restriction.createInitialState()
        for (eachRestriction in restrictions) {
            restriction = restriction.addRestrict(eachRestriction.first, eachRestriction.second)
        }
        return User(
            id = 0,
            email = "TestEmail@Kakako",
            password = "TestPassword",
            role = Role.USER,
            name = "TestUser",
            introduction = UserIntroduction.from("hello"),
            gender = Gender.MALE,
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
            restriction = restriction,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            withdrawalStatus = WithdrawalStatus.NONE,
            withdrawalRequestAt = null,
            withdrawalCancelAt = null,
            withdrawalCompleteAt = null
        )
    }

}
