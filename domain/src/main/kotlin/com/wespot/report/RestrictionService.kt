package com.wespot.report

import com.wespot.user.Restriction
import com.wespot.user.RestrictionType
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
        validateReportType(newReport, previousReports)

        if (newReport.isMessageReport()) {
            return restrictByMessageReport(previousReports, newReport, originRestriction)
        }

        return restrictByVoteReport(previousReports, originRestriction)
    }

    private fun validateReportType(newReport: Report, previousReports: List<Report>) {
        if (isSameReportType(previousReports, newReport)) {
            return
        }

        throw IllegalArgumentException("새로 들어온 신고의 타입과 동일한 신고들을 다루어야합니다.")
    }

    private fun isSameReportType(
        previousReports: List<Report>,
        newReport: Report
    ) = previousReports.stream()
        .allMatch { it.reportType == newReport.reportType }


    private fun restrictByMessageReport(
        previousReports: List<Report>,
        newReport: Report,
        originRestriction: Restriction
    ): Restriction {
        if (validateDuplicateReport(previousReports, newReport)) {
            throw IllegalArgumentException("쪽지 하나를 여러 번 신고할 수 없습니다.")
        }
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
