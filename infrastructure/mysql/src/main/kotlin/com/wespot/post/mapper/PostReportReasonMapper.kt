package com.wespot.post.mapper

import com.wespot.post.PostReportReason
import com.wespot.post.PostReportReasonEntity
import com.wespot.report.ReportReason
import com.wespot.report.ReportReasonWithCustomReason

object PostReportReasonMapper {

    fun toDomain(
        postReportReasonEntity: PostReportReasonEntity,
        reportReason: ReportReason
    ): PostReportReason {
        return PostReportReason(
            id = postReportReasonEntity.id,
            postReportId = postReportReasonEntity.postReportId,
            reportReasonWithCustomReason = ReportReasonWithCustomReason(
                reportReason = reportReason,
                customReason = postReportReasonEntity.customReason
            ),

            createdAt = postReportReasonEntity.baseEntity.createdAt,
            updatedAt = postReportReasonEntity.baseEntity.updatedAt
        )
    }

    fun toEntity(postReportReason: PostReportReason): PostReportReasonEntity {
        return PostReportReasonEntity(
            id = postReportReason.id,
            postReportId = postReportReason.postReportId,
            reportReasonId = postReportReason.reportReasonWithCustomReason.reportReason.id,
            customReason = postReportReason.reportReasonWithCustomReason.customReason,
            baseEntity = com.wespot.common.BaseEntity(
                createdAt = postReportReason.createdAt,
                updatedAt = postReportReason.updatedAt
            )
        )
    }

}
