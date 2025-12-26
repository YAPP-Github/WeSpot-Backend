package com.wespot.user.fixture

import com.wespot.auth.PrincipalDetails
import com.wespot.school.School
import com.wespot.school.SchoolJpaEntity
import com.wespot.school.SchoolMapper
import com.wespot.school.fixture.SchoolFixture
import com.wespot.user.*
import com.wespot.user.dto.request.UpdateProfileRequest
import com.wespot.user.restriction.Restriction
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.time.LocalDateTime

object UserFixture {

    fun createWithId(
        id: Long,
        schoolJpaEntity: SchoolJpaEntity = SchoolFixture.generateJpaEntity()
    ) = User(
        id = id,
        email = "TestEmail@Kakako",
        password = "TestPassword",
        role = Role.USER,
        name = "TestUser",
        introduction = UserIntroduction.from("hello"),
        gender = Gender.MALE,
        school = SchoolMapper.mapToDomainEntity(schoolJpaEntity),
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
        withdrawalCompleteAt = null,
        userPolicyAgreements =  emptyList()
    )

    fun createWithIdSchool(
        id: Long,
        school: School = SchoolFixture.generate()
    ) = User(
        id = id,
        email = "TestEmail@Kakako",
        password = "TestPassword",
        role = Role.USER,
        name = "TestUser",
        introduction = UserIntroduction.from("hello"),
        gender = Gender.MALE,
        school = school,
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
        withdrawalCompleteAt = null,
        userPolicyAgreements =  emptyList()
    )

    fun createWithIdAndEmail(
        id: Long,
        email: String,
        school: SchoolJpaEntity = SchoolFixture.generateJpaEntity()
    ) = User(
        id = id,
        email = email,
        password = "TestPassword",
        role = Role.USER,
        name = "TestUser",
        introduction = UserIntroduction.from("hello"),
        gender = Gender.MALE,
        school = SchoolMapper.mapToDomainEntity(school),
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
        withdrawalCompleteAt = null,
        userPolicyAgreements =  emptyList()
    )

    fun createSender(
        id: Long = 1L,
        school: SchoolJpaEntity = SchoolFixture.generateJpaEntity()
    ) = User(
        id = id,
        email = "sender@example.com",
        password = "password",
        role = Role.USER,
        name = "Sender",
        introduction = UserIntroduction.from("intro"),
        gender = Gender.MALE,
        school = SchoolMapper.mapToDomainEntity(school),
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
        withdrawalCompleteAt = null,
        userPolicyAgreements =  emptyList()
    )

    fun createReceiver(
        id: Long = 2L,
        school: SchoolJpaEntity = SchoolFixture.generateJpaEntity()
    ) = User(
        id = id,
        email = "receiver@example.com",
        password = "password",
        role = Role.USER,
        name = "Receiver",
        introduction = UserIntroduction.from("intro"),
        gender = Gender.MALE,
        school = SchoolMapper.mapToDomainEntity(school),
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
        withdrawalCompleteAt = null,
        userPolicyAgreements =  emptyList()
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
        school: SchoolJpaEntity = SchoolFixture.generateJpaEntity(),
        profileId: Long = id
    ): User {
        return User(
            id = id,
            email = email,
            password = "password",
            name = name,
            introduction = UserIntroduction.from("소개 $id"),
            gender = Gender.MALE,
            school = SchoolMapper.mapToDomainEntity(school),
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
            updatedAt = LocalDateTime.now(),
            userPolicyAgreements =  emptyList()
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
        school = SchoolFixture.generate(id = schoolId),
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
        withdrawalCompleteAt = null,
        userPolicyAgreements =  emptyList()
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
        school = SchoolFixture.generate(id = schoolId),
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
        withdrawalCompleteAt = null,
        userPolicyAgreements =  emptyList()
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
        school = SchoolFixture.generate(id = schoolId),
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
        withdrawalCompleteAt = null,
        userPolicyAgreements =  emptyList()
    )

    fun createUserWithRestrictionTypeAndRestrictDay(
        school: SchoolJpaEntity = SchoolFixture.generateJpaEntity(),
        restrictions: List<Pair<RestrictionType, Long>>
    ): User {
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
            school = SchoolMapper.mapToDomainEntity(school),
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
            withdrawalCompleteAt = null,
            userPolicyAgreements =  emptyList()
        )
    }

    fun createUserWithNameAndEmailAndRestrictionTypeAndRestrictDayAndSchoolId(
        name: String,
        email: String,
        restrictions: List<Pair<RestrictionType, Long>>,
        school: SchoolJpaEntity = SchoolFixture.generateJpaEntity(),
    ): User {
        var restriction = Restriction.createInitialState()
        for (eachRestriction in restrictions) {
            restriction = restriction.addRestrict(eachRestriction.first, eachRestriction.second)
        }
        return User(
            id = 0,
            email = email,
            password = "TestPassword",
            role = Role.USER,
            name = name,
            introduction = UserIntroduction.from("hello"),
            gender = Gender.MALE,
            school = SchoolMapper.mapToDomainEntity(school),
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
            withdrawalCompleteAt = null,
            userPolicyAgreements =  emptyList()
        )
    }

    fun createUserWithIdAndRestrictionTypeAndRestrictDay(
        id: Long,
        school: School = SchoolFixture.generate(id = 1),
        restrictions: List<Pair<RestrictionType, Long>>
    ): User {
        var restriction = Restriction.createInitialState()
        for (eachRestriction in restrictions) {
            restriction = restriction.addRestrict(eachRestriction.first, eachRestriction.second)
        }
        return User(
            id = id,
            email = "TestEmail@Kakako",
            password = "TestPassword",
            role = Role.USER,
            name = "TestUser",
            introduction = UserIntroduction.from("hello"),
            gender = Gender.MALE,
            school = school,
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
            withdrawalCompleteAt = null,
            userPolicyAgreements =  emptyList()
        )
    }

}
