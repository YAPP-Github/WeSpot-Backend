package com.wespot.report

import com.wespot.user.User

data class Report(
    val id: Long,
    val reportType: ReportType,
    val targetId: Long,
    val senderId: Long,
    val receiverId: Long,
) {

    companion object {

        fun of(
            reportType: ReportType,
            targetId: Long,
            sender: User,
            receiver: User
        ): Report {
            return Report(
                id = 0,
                reportType = reportType,
                targetId = targetId,
                senderId = sender.id,
                receiverId = receiver.id
            )
        }

    }

    fun isMessageReport(): Boolean {
        return reportType == ReportType.MESSAGE
    }

    fun isVoteReport(): Boolean {
        return reportType == ReportType.VOTE
    }

    fun isSameReport(otherReport: Report): Boolean {
        return reportType == otherReport.reportType
            && targetId == otherReport.targetId
            && senderId == otherReport.senderId
            && receiverId == otherReport.receiverId
    }

}
