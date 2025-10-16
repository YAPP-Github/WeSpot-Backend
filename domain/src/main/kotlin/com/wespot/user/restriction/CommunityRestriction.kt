package com.wespot.user.restriction

import com.wespot.user.RestrictionType
import java.time.LocalDate

data class CommunityRestriction(
    val restrictionType: RestrictionType = RestrictionType.NONE,
    val releaseDate: LocalDate = PERMANENT_BAN_DATE
) {

    companion object {

        private val PERMANENT_BAN_DATE = LocalDate.of(9999, 12, 31)
        private const val PERMANENT_BAN_DAY = Long.MAX_VALUE

        private val RESTRICTION_RULE: List<RestrictionRule> = listOf(
            RestrictionRule(20, PERMANENT_BAN_DAY),
            RestrictionRule(15, 15),
            RestrictionRule(10, 7),
            RestrictionRule(5, 3),
            RestrictionRule(0, 0),
        )

    }

    fun getCurrentRestrictionBasedOnTime(date: LocalDate): CommunityRestriction {
        if (releaseDate.isBefore(date)) {
            return CommunityRestriction()
        }

        return this
    }

    fun isKeepRestriction(): Boolean {
        return restrictionType != RestrictionType.NONE
    }

    fun receivedNewReport(reportCount: Long): CommunityRestriction {
        val restrictionDay = RESTRICTION_RULE
            .firstOrNull { reportCount >= it.standard }
            ?.restrictionDay ?: 0L

        if (restrictionDay == 0L) {
            return this
        }

        if (restrictionDay == PERMANENT_BAN_DAY) {
            return CommunityRestriction(RestrictionType.PERMANENT_BAN_COMMUNITY_REPORT, PERMANENT_BAN_DATE)
        }

        return CommunityRestriction(
            RestrictionType.TEMPORARY_BAN_COMMUNITY_REPORT,
            LocalDate.now().plusDays(restrictionDay)
        )
    }

}
