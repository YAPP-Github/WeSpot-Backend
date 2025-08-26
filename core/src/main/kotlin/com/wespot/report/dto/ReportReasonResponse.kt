package com.wespot.report.dto

import com.wespot.report.ReportReason

data class ReportReasonResponse(
    val id: Long,
    val reason: String
) {

    companion object {
        fun from(reportReason: ReportReason): ReportReasonResponse {
            return ReportReasonResponse(
                id = reportReason.id,
                reason = reportReason.content
            )
        }
    }

}
