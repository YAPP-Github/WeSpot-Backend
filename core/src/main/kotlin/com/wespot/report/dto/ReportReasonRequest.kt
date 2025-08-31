package com.wespot.report.dto

data class ReportReasonRequest(
    val reportReasonId: Long,
    val customReason: String?
) {
}
