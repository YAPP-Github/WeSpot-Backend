package com.wespot.comment.mapper

import com.wespot.comment.PostCommentReport
import com.wespot.comment.PostCommentReportReason
import com.wespot.comment.PostCommentReportReasonEntity
import com.wespot.common.BaseEntity
import com.wespot.report.ReportReason
import com.wespot.report.ReportReasonWithCustomReason

object PostCommentReportReasonMapper {

    fun toDomain(
        postCommentReportReasonEntity: PostCommentReportReasonEntity,
        reportReason: ReportReason
    ): PostCommentReportReason {
        return PostCommentReportReason(
            id = postCommentReportReasonEntity.id,
            postCommentReportId = postCommentReportReasonEntity.postCommentReportId,
            reportReasonWithCustomReason = ReportReasonWithCustomReason(
                reportReason = reportReason,
                customReason = postCommentReportReasonEntity.customReason
            ),

            createdAt = postCommentReportReasonEntity.baseEntity.createdAt,
            updatedAt = postCommentReportReasonEntity.baseEntity.updatedAt
        )
    }

    fun toEntity(postCommentReportReason: PostCommentReportReason): PostCommentReportReasonEntity {
        return PostCommentReportReasonEntity(
            id = postCommentReportReason.id,
            postCommentReportId = postCommentReportReason.postCommentReportId,
            reportReasonId = postCommentReportReason.reportReasonWithCustomReason.reportReason.id,
            customReason = postCommentReportReason.reportReasonWithCustomReason.customReason,

            baseEntity = BaseEntity(
                createdAt = postCommentReportReason.createdAt,
                updatedAt = postCommentReportReason.updatedAt
            )
        )
    }

}
