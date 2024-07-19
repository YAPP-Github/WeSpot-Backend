package com.wespot.user

import java.time.LocalDateTime

data class User(
    val id: Long,
    val email: String,
    val password: String,
    val name: String,
    val introduction: String,
    val role: Role,
    val schoolId: Long,
    val grade: Int,
    val groupNumber: Int,
    val profile: Profile,
    val fcm: FCM?,
    val setting: Setting,
    val social: Social,
    val userConsent: UserConsent,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val withdrawAt: LocalDateTime?,
) {

    fun updateProfile(
        introduction: String,
        backgroundColor: String,
        iconUrl: String
    ) =
        User(
            id = id,
            email = email,
            password = password,
            name = name,
            introduction = introduction,
            role = role,
            schoolId = schoolId,
            grade = grade,
            groupNumber = groupNumber,
            profile = Profile(
                id = profile.id,
                backgroundColor = backgroundColor,
                iconUrl = iconUrl
            ),
            fcm = fcm,
            setting = setting,
            social = social,
            userConsent = userConsent,
            createdAt = createdAt,
            updatedAt = LocalDateTime.now(),
            withdrawAt = withdrawAt,
        )

    fun withdraw() =
        User(
            id = id,
            email = "",
            password = "",
            name = "",
            introduction = "",
            role = Role.GUEST,
            schoolId = schoolId,
            grade = grade,
            groupNumber = groupNumber,
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
            updatedAt = LocalDateTime.now(),
            withdrawAt = LocalDateTime.now(),
        )

    companion object {
        fun create(
            email: String,
            password: String,
            name: String,
            introduction: String,
            schoolId: Long,
            grade: Int,
            groupNumber: Int,
            social: Social
        ) =
            User(
                id = 0L,
                email = email,
                password = password,
                name = name,
                introduction = introduction,
                role = Role.USER,
                schoolId = schoolId,
                grade = grade,
                groupNumber = groupNumber,
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
                role = user.role,
                schoolId = user.schoolId,
                grade = user.grade,
                groupNumber = user.groupNumber,
                profile = profile ?: user.profile,
                fcm = fcm ?: user.fcm,
                setting = setting ?: user.setting,
                social = user.social,
                userConsent = userConsent ?: user.userConsent,
                createdAt = user.createdAt,
                updatedAt = LocalDateTime.now(),
                withdrawAt = user.withdrawAt
            )

    }
}
