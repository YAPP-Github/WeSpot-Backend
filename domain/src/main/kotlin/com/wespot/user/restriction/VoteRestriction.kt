package com.wespot.user.restriction

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.RestrictionType
import com.wespot.user.restriction.MessageRestriction.Companion
import org.springframework.http.HttpStatus
import java.time.LocalDate

data class VoteRestriction(
    val restrictionType: RestrictionType = RestrictionType.NONE,
    val releaseDate: LocalDate = PERMANENT_BAN_DATE,
) {

    companion object {

        private val PERMANENT_BAN_DATE = LocalDate.of(9999, 12, 31)
        private const val PERMANENT_BAN_DAY = Long.MAX_VALUE

        private val RESTRICTION_RULE: List<RestrictionRule> = listOf(
            RestrictionRule(15, PERMANENT_BAN_DAY),
            RestrictionRule(0, 0),
        )


        fun createInitialState() = VoteRestriction()

        fun of(restrictionType: RestrictionType, restrictionDay: Long): VoteRestriction {
            validate(restrictionType, restrictionDay)

            return VoteRestriction(restrictionType, PERMANENT_BAN_DATE)
        }

        private fun validate(restrictionType: RestrictionType, restrictionDay: Long) {
            require(restrictionType.isVoteRestriction()) {
                throw CustomException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ExceptionView.TOAST,
                    "투표로 인한 제재 타입을 입력해주세요."
                )
            }
            require(
                restrictionType == RestrictionType.PERMANENT_BAN_VOTE_REPORT && restrictionDay == PERMANENT_BAN_DAY
            ) {
                throw CustomException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ExceptionView.TOAST,
                    "올바르지 않은 제재 타입과 제재 일 수 입니다."
                )
            }
        }

    }

    fun getCurrentRestrictionBasedOnTime(date: LocalDate): VoteRestriction {
        if (releaseDate.isBefore(date)) {
            return createInitialState()
        }

        return VoteRestriction(restrictionType, releaseDate)
    }

    fun isKeepRestriction() = restrictionType != RestrictionType.NONE

    fun receivedNewReport(reportCount: Long): VoteRestriction {
        val restrictionDay = RESTRICTION_RULE
            .firstOrNull { reportCount >= it.standard }
            ?.restrictionDay ?: 0L

        if (restrictionDay == 0L) {
            return this
        }

        return VoteRestriction(
            RestrictionType.PERMANENT_BAN_VOTE_REPORT,
            PERMANENT_BAN_DATE
        )
    }

}
