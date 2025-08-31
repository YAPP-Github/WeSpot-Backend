package com.wespot.comment.dto

import com.wespot.report.dto.ReportReasonRequest

data class PostCommentReportRequest(
    val reportReasonRequests: List<ReportReasonRequest>,
) {
}
