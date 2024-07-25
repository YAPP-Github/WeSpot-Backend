package com.wespot.report

import com.wespot.user.Restriction
import com.wespot.user.RestrictionType
import com.wespot.user.User
import org.springframework.stereotype.Component

@Component
class RestrictionService {

    companion object {
        const val FIRST_MESSAGE_USAGE_RESTRICTION_COUNT = 5
        const val FIRST_MESSAGE_USAGE_RESTRICTION_DAY = 30L
        const val SECOND_MESSAGE_USAGE_RESTRICTION_COUNT = 10
        const val SECOND_MESSAGE_USAGE_RESTRICTION_DAY = 90L
        const val MESSAGE_PERMANENT_RESTRICTION_COUNT = 15
        const val VOTE_PERMANENT_RESTRICTION_COUNT = 15
        const val PERMANENT_BAN_DAY = Long.MAX_VALUE
    }

    fun calculateRestrictionByReports(
        originRestriction: Restriction,
        newReport: Report,
        previousReports: List<Report>
    ): Restriction {
        if (newReport.isMessageReport()) {
            return restrictByMessageReport(previousReports, newReport, originRestriction)
        }

        return restrictByVoteReport(previousReports, originRestriction)
    }

    private fun restrictByMessageReport(
        previousReports: List<Report>,
        newReport: Report,
        originRestriction: Restriction
    ): Restriction {
        validateDuplicateReport(previousReports, newReport)
        val reportsCount = previousReports.size + 1
        if (reportsCount == FIRST_MESSAGE_USAGE_RESTRICTION_COUNT) {
            return Restriction.of(RestrictionType.MESSAGE_USAGE, FIRST_MESSAGE_USAGE_RESTRICTION_DAY)
        }
        if (reportsCount == SECOND_MESSAGE_USAGE_RESTRICTION_COUNT) {
            return Restriction.of(RestrictionType.MESSAGE_USAGE, SECOND_MESSAGE_USAGE_RESTRICTION_DAY)
        }
        if (reportsCount == MESSAGE_PERMANENT_RESTRICTION_COUNT) {
            return Restriction.of(RestrictionType.MESSAGE_PERMANENT, PERMANENT_BAN_DAY)
        }

        return originRestriction
    }

    private fun validateDuplicateReport(
        previousReports: List<Report>,
        newReport: Report
    ) = previousReports.stream()
        .anyMatch { it.isSameReport(newReport) }


    private fun restrictByVoteReport(
        previousReports: List<Report>,
        originRestriction: Restriction
    ): Restriction {
        val reportsCount = previousReports.size + 1

        if (reportsCount == VOTE_PERMANENT_RESTRICTION_COUNT) {
            return Restriction.of(RestrictionType.VOTE_PERMANENT, PERMANENT_BAN_DAY)
        }

        return originRestriction
    }

}
