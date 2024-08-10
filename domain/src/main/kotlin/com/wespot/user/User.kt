package com.wespot.user

import com.wespot.user.restriction.Restriction
import java.time.LocalDate
import java.time.LocalDateTime

data class User(
    val id: Long,
    val email: String,
    val password: String,
    val name: String,
    val introduction: UserIntroduction,
    val gender: String,
    val role: Role,
    val schoolId: Long,
    val grade: Int,
    val classNumber: Int,
    val profile: Profile,
    val fcm: FCM?,
    var setting: Setting,
    val social: Social,
    val userConsent: UserConsent,
    var restriction: Restriction,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val withdrawAt: LocalDateTime?,
) {

    fun updateProfile(
        introduction: String,
    ) =
        User(
            id = id,
            email = email,
            password = password,
            name = name,
            introduction = UserIntroduction.from(introduction),
            gender = gender,
            role = role,
            schoolId = schoolId,
            grade = grade,
            classNumber = classNumber,
            profile = profile,
            fcm = fcm,
            setting = setting,
            social = social,
            userConsent = userConsent,
            restriction = restriction,
            createdAt = createdAt,
            updatedAt = LocalDateTime.now(),
            withdrawAt = withdrawAt,
        )

    fun withdraw() =
        User(
            id = id,
            email = "",
            password = "",
            name = WITHDRAW_USER_NAME,
            introduction = UserIntroduction.emptyUserIntroduction(),
            gender = gender,
            role = Role.GUEST,
            schoolId = schoolId,
            grade = grade,
            classNumber = classNumber,
            profile = profile,
            fcm = fcm,
            setting = setting,
            social = Social(
                socialEmail = "",
                socialId = "",
                socialType = social.socialType,
                socialRefreshToken = ""
            ),
            userConsent = userConsent,
            createdAt = createdAt,
            restriction = restriction,
            updatedAt = LocalDateTime.now(),
            withdrawAt = LocalDateTime.now(),
        )

    companion object {

        private const val WITHDRAW_USER_NAME = "탈퇴한 유저입니다."

        fun create(
            email: String,
            password: String,
            name: String,
            schoolId: Long,
            grade: Int,
            groupNumber: Int,
            social: Social,
            gender: String
        ) =
            User(
                id = 0L,
                email = email,
                password = password,
                name = name,
                introduction = UserIntroduction.emptyUserIntroduction(),
                gender = gender,
                role = Role.USER,
                schoolId = schoolId,
                grade = grade,
                classNumber = groupNumber,
                profile = Profile.create(
                    backgroundColor = "",
                    iconUrl = "",
                ),
                fcm = null,
                setting = Setting(),
                social = social,
                userConsent = UserConsent.create(
                    consentType = ConsentType.MARKETING,
                    consentedAt = LocalDateTime.now(),
                    consentValue = false
                ),
                restriction = Restriction.createInitialState(),
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                withdrawAt = null
            )

        fun update(
            user: User,
            profile: Profile?,
            fcm: FCM?,
            setting: Setting?,
            userConsent: UserConsent?
        ) =
            User(
                id = user.id,
                email = user.email,
                password = user.password,
                name = user.name,
                introduction = user.introduction,
                gender = user.gender,
                role = user.role,
                schoolId = user.schoolId,
                grade = user.grade,
                classNumber = user.classNumber,
                profile = profile ?: user.profile,
                fcm = fcm ?: user.fcm,
                setting = setting ?: user.setting,
                social = user.social,
                userConsent = userConsent ?: user.userConsent,
                restriction = user.restriction,
                createdAt = user.createdAt,
                updatedAt = LocalDateTime.now(),
                withdrawAt = user.withdrawAt
            )

    }

    fun restrict(
        restriction: Restriction
    ) {
        this.restriction = restriction
    }

    fun changeSettings(
        isEnableVoteNotification: Boolean,
        isEnableMessageNotification: Boolean,
        isEnableMarketingNotification: Boolean
    ) {
        this.setting = Setting(isEnableVoteNotification, isEnableMessageNotification, isEnableMarketingNotification)
    }

    fun isEnableVoteNotification() = setting.isEnableVoteNotification

    fun isEnableMessageNotification() = setting.isEnableMessageNotification

    fun isEnableMarketingNotification() = setting.isEnableMarketingNotification

    fun getCurrentUserRestrictionBasedOnTime(date: LocalDate) =
        restriction.getCurrentRestrictionBasedOnTime(date)

    fun isClassmate(
        otherUser: User
    ) = this.schoolId == otherUser.schoolId
        && this.grade == otherUser.grade
        && this.classNumber == otherUser.classNumber

}
