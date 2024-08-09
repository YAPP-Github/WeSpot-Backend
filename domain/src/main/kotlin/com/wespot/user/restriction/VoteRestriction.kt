package com.wespot.user.restriction

import com.wespot.user.RestrictionType
import java.time.LocalDate

data class VoteRestriction(
    val restrictionType: RestrictionType,
    val releaseDate: LocalDate,
) {

    companion object {

        private val PERMANENT_BAN_DATE = LocalDate.of(9999, 12, 31)
        private const val PERMANENT_BAN_DAY = Long.MAX_VALUE

        fun createInitialState() = VoteRestriction(
            restrictionType = RestrictionType.NONE,
            releaseDate = PERMANENT_BAN_DATE
        )

        fun of(restrictionType: RestrictionType, restrictionDay: Long): VoteRestriction {
            validate(restrictionType, restrictionDay)

            return VoteRestriction(restrictionType, PERMANENT_BAN_DATE)
        }

        private fun validate(restrictionType: RestrictionType, restrictionDay: Long) {
            require(restrictionType.isVoteRestriction()) { "투표로 인한 제재 타입을 입력해주세요." }
            require(
                restrictionType == RestrictionType.PERMANENT_BAN_VOTE_REPORT && restrictionDay == PERMANENT_BAN_DAY
            ) { "올바르지 않은 제재 타입과 제재 일 수 입니다." }
        }

    }


    fun getCurrentRestrictionBasedOnTime(date: LocalDate): VoteRestriction {
        if (releaseDate.isBefore(date)) {
            return createInitialState()
        }

        return VoteRestriction(restrictionType, releaseDate)
    }

    fun isKeepRestriction() = restrictionType != RestrictionType.NONE

}
