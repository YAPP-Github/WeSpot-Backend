package com.wespot.user

import java.time.LocalDate

data class Restriction(
    val restrictionType: RestrictionType,
    val releaseDate: LocalDate
) {

    companion object {

        private val PERMANENT_BAN_DATE = LocalDate.MAX
        private const val PERMANENT_BAN_DAY = Long.MAX_VALUE
        private const val FIRST_MESSAGE_USAGE_RESTRICTION_DAY = 30L
        private const val SECOND_MESSAGE_USAGE_RESTRICTION_DAY = 90L

        fun createInitialState() =
            Restriction(
                restrictionType = RestrictionType.NONE,
                releaseDate = PERMANENT_BAN_DATE
            )

        fun of(restrictionType: RestrictionType, restrictionDay: Long): Restriction {
            validate(restrictionType, restrictionDay)

            if (restrictionDay == PERMANENT_BAN_DAY) {
                return Restriction(restrictionType, PERMANENT_BAN_DATE)
            }

            return Restriction(restrictionType, LocalDate.now().plusDays(restrictionDay))
        }

        private fun validate(restrictionType: RestrictionType, restrictionDay: Long) {
            if ((restrictionType == RestrictionType.PERMANENT_BAN_VOTE_REPORT && restrictionDay == PERMANENT_BAN_DAY)
                || (restrictionType == RestrictionType.PERMANENT_BAN_MESSAGE_REPORT && restrictionDay == PERMANENT_BAN_DAY)
            ) {
                return
            }
            if ((restrictionType == RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT && restrictionDay == FIRST_MESSAGE_USAGE_RESTRICTION_DAY)
                || (restrictionType == RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT && restrictionDay == SECOND_MESSAGE_USAGE_RESTRICTION_DAY)
            ) {
                return
            }
            throw IllegalArgumentException("올바르지 않은 제재 타입과 제재 일 수 입니다.")
        }

    }

    fun getCurrentRestrictionBasedOnTime(date: LocalDate): Restriction {
        if (releaseDate.isBefore(date)) {
            return createInitialState()
        }

        return Restriction(restrictionType, releaseDate)
    }

}
