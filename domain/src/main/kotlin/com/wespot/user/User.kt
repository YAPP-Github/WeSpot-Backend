package com.wespot.user

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.school.School
import com.wespot.user.restriction.Restriction
import org.springframework.http.HttpStatus
import java.time.LocalDate
import java.time.LocalDateTime

data class User(
    val id: Long,
    val email: String,
    val password: String,
    val name: String,
    val introduction: UserIntroduction,
    val gender: Gender,
    val role: Role,
    val school: School,
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
    val withdrawalStatus: WithdrawalStatus,
    val withdrawalRequestAt: LocalDateTime?,
    val withdrawalCancelAt: LocalDateTime?,
    val withdrawalCompleteAt: LocalDateTime?,
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
            school = school,
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
            withdrawalStatus = withdrawalStatus,
            withdrawalRequestAt = withdrawalRequestAt,
            withdrawalCancelAt = withdrawalCancelAt,
            withdrawalCompleteAt = withdrawalCompleteAt,
        )

    fun withdraw() =
        User(
            id = id,
            email = email,
            password = password,
            name = name,
            introduction = introduction,
            gender = gender,
            role = role,
            school = school,
            grade = grade,
            classNumber = classNumber,
            profile = profile,
            fcm = fcm,
            setting = setting,
            social = social,
            userConsent = userConsent,
            createdAt = createdAt,
            restriction = restriction,
            updatedAt = LocalDateTime.now(),
            withdrawalStatus = WithdrawalStatus.ACTIVE,
            withdrawalRequestAt = LocalDateTime.now(),
            withdrawalCancelAt = null,
            withdrawalCompleteAt = null,
        )

    fun cancelWithdraw(): User {
        isWithdrawActive()
        return User(
            id = id,
            email = email,
            password = password,
            name = name,
            introduction = introduction,
            gender = gender,
            role = role,
            school = school,
            grade = grade,
            classNumber = classNumber,
            profile = profile,
            fcm = fcm,
            setting = setting,
            social = social,
            userConsent = userConsent,
            createdAt = createdAt,
            restriction = restriction,
            updatedAt = LocalDateTime.now(),
            withdrawalStatus = WithdrawalStatus.CANCELED,
            withdrawalRequestAt = withdrawalRequestAt,
            withdrawalCancelAt = LocalDateTime.now(),
            withdrawalCompleteAt = null,
        )
    }


    fun completeWithdraw(
        profile: Profile
    ) = User(
        id = id,
        email = "",
        password = "",
        name = WITHDRAW_USER_NAME,
        introduction = UserIntroduction.emptyUserIntroduction(),
        gender = gender,
        role = Role.GUEST,
        school = school,
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
        withdrawalStatus = WithdrawalStatus.WITHDRAWN,
        withdrawalRequestAt = withdrawalRequestAt,
        withdrawalCancelAt = withdrawalCancelAt,
        withdrawalCompleteAt = LocalDateTime.now(),
    )

    companion object {

        private const val WITHDRAW_USER_NAME = "탈퇴한 유저입니다."
        const val EVER_NAME = "위스팟 행성의 에버"

        fun create(
            email: String,
            password: String,
            name: String,
            school: School,
            grade: Int,
            groupNumber: Int,
            social: Social,
            gender: Gender,
            introduction: String?,
        ) = User(
            id = 0L,
            email = email,
            password = password,
            name = name,
            introduction = UserIntroduction.fromNullable(introduction),
            gender = gender,
            role = Role.USER,
            school = school,
            grade = grade,
            classNumber = groupNumber,
            profile = Profile.createInit(null),
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
            withdrawalStatus = WithdrawalStatus.NONE,
            withdrawalRequestAt = null,
            withdrawalCancelAt = null,
            withdrawalCompleteAt = null,
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
                school = user.school,
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
                withdrawalStatus = user.withdrawalStatus,
                withdrawalRequestAt = user.withdrawalRequestAt,
                withdrawalCancelAt = user.withdrawalCancelAt,
                withdrawalCompleteAt = user.withdrawalCompleteAt
            )

    }

    private fun isWithdrawActive() {
        if (withdrawalStatus != WithdrawalStatus.ACTIVE) {
            throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "탈퇴가 진행 중이 아닙니다.")
        }
    }

    fun restrict(
        restriction: Restriction
    ) {
        this.restriction = restriction
    }

    fun changeSettings(
        isEnableVoteNotification: Boolean? = null,
        isEnableMessageNotification: Boolean? = null,
        isEnableMarketingNotification: Boolean? = null,
        isEnableMessage: Boolean? = null,
        isEnablePostNotification: Boolean? = null
    ) {
        isEnableVoteNotification?.let { this.setting = this.setting.copy(isEnableVoteNotification = it) }
        isEnableMessageNotification?.let { this.setting = this.setting.copy(isEnableMessageNotification = it) }
        isEnableMarketingNotification?.let { this.setting = this.setting.copy(isEnableMarketingNotification = it) }
        isEnableMessage?.let { this.setting = this.setting.copy(isEnableMessage = it) }
        isEnablePostNotification?.let { this.setting = this.setting.copy(isEnablePostNotification = it) }
    }

    fun isEnableVoteNotification() = setting.isEnableVoteNotification

    fun isEnableMessageNotification() = setting.isEnableMessageNotification

    fun isEnableMarketingNotification() = setting.isEnableMarketingNotification

    fun getCurrentUserRestrictionBasedOnTime(date: LocalDate) =
        restriction.getCurrentRestrictionBasedOnTime(date)

    fun isClassmate(
        otherUser: User
    ) = this.school.id == otherUser.school.id
        && this.grade == otherUser.grade
        && this.classNumber == otherUser.classNumber

    fun isKeepRestrict(): Boolean {
        return restriction.isKeepRestriction()
    }

    fun isWithDraw(): Boolean {
        return withdrawalStatus == WithdrawalStatus.WITHDRAWN
    }

    fun isRegulation(): Boolean {
        return isWithDraw() || isKeepRestrict()
    }

    fun updateIntroduction(introduction: String?): User {
        return copy(introduction = UserIntroduction.fromNullable(introduction))
    }

    fun logout(
        saveFcm: (fcm: FCM) -> FCM
    ) {
        fcm?.let {
            val noContentFcm = fcm.clearFcmToken()
            saveFcm(noContentFcm)
        }
    }

    fun isMeSender(senderId: Long): Boolean {
        return id == senderId
    }

    fun isMeReceiver(receiverId: Long): Boolean {
        return id == receiverId
    }

    fun isEver(): Boolean {
        return name == EVER_NAME
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun isSameUser(user: User): Boolean {
        return this.id == user.id
    }

    fun isEnableMessage(): Boolean {
        if (isWithDraw() || isKeepRestrict()) {
            return false
        }
        return setting.isEnableMessage
    }

    fun isEnablePostNotification(): Boolean {
        if (isWithDraw() || isKeepRestrict()) {
            return false
        }
        return setting.isEnablePostNotification
    }

}
