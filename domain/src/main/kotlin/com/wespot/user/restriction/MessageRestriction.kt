package com.wespot.user.restriction

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.RestrictionType
import com.wespot.user.restriction.CommunityRestriction.Companion
import org.springframework.http.HttpStatus
import java.time.LocalDate

data class MessageRestriction(
    val restrictionType: RestrictionType = RestrictionType.NONE,
    val releaseDate: LocalDate = PERMANENT_BAN_DATE
) {

    companion object {

        private val PERMANENT_BAN_DATE = LocalDate.of(9999, 12, 31)
        private const val PERMANENT_BAN_DAY = Long.MAX_VALUE
        private const val FIRST_MESSAGE_USAGE_RESTRICTION_DAY = 30L
        private const val SECOND_MESSAGE_USAGE_RESTRICTION_DAY = 90L

        private val RESTRICTION_RULE: List<RestrictionRule> = listOf(
            RestrictionRule(20, PERMANENT_BAN_DAY),
            RestrictionRule(15, 15),
            RestrictionRule(10, 7),
            RestrictionRule(5, 3),
            RestrictionRule(0, 0),
        )

        fun createInitialState() =
            MessageRestriction()

        fun of(restrictionType: RestrictionType, restrictionDay: Long): MessageRestriction {
            validate(restrictionType, restrictionDay)

            if (restrictionDay == PERMANENT_BAN_DAY) {
                return MessageRestriction(restrictionType, PERMANENT_BAN_DATE)
            }

            return MessageRestriction(restrictionType, LocalDate.now().plusDays(restrictionDay))
        }

        private fun validate(restrictionType: RestrictionType, restrictionDay: Long) {
            require(restrictionType.isMessageRestriction()) {
                throw CustomException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ExceptionView.TOAST,
                    "쪽지로 인한 제재 타입을 입력해주세요."
                )
            }
            require(
                (restrictionType == RestrictionType.PERMANENT_BAN_MESSAGE_REPORT && restrictionDay == PERMANENT_BAN_DAY)
                    || (restrictionType == RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT && restrictionDay == FIRST_MESSAGE_USAGE_RESTRICTION_DAY)
                    || (restrictionType == RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT && restrictionDay == SECOND_MESSAGE_USAGE_RESTRICTION_DAY)
            ) {
                throw CustomException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ExceptionView.TOAST,
                    "올바르지 않은 제재 타입과 제재 일 수 입니다."
                )
            }
        }

    }

    fun getCurrentRestrictionBasedOnTime(date: LocalDate): MessageRestriction {
        if (releaseDate.isBefore(date)) {
            return createInitialState()
        }

        return MessageRestriction(restrictionType, releaseDate)
    }

    fun isKeepRestriction() = restrictionType != RestrictionType.NONE

    fun receivedNewReport(reportCount: Long): MessageRestriction {
        val restrictionDay = RESTRICTION_RULE
            .firstOrNull { reportCount >= it.standard }
            ?.restrictionDay ?: 0L

        if (restrictionDay == 0L) {
            return this
        }

        if (restrictionDay == PERMANENT_BAN_DAY) {
            return MessageRestriction(
                RestrictionType.PERMANENT_BAN_MESSAGE_REPORT,
                PERMANENT_BAN_DATE
            )
        }

        return MessageRestriction(
            RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT,
            LocalDate.now().plusDays(restrictionDay)
        )
    }

}
