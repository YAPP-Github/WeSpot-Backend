package com.wespot.comment.mapper

import com.wespot.comment.CommentReport
import com.wespot.comment.CommentReportEntity
import com.wespot.common.BaseEntity
import java.time.LocalDateTime

object CommentReportMapper {

    fun toEntity(commentReport: CommentReport): CommentReportEntity {
        return CommentReportEntity(
            id = commentReport.id,
            commentId = commentReport.commentId,
            userId = commentReport.userId,
            baseEntity = BaseEntity(createdAt = commentReport.createdAt, updatedAt = LocalDateTime.now())
        )
    }

    fun toDomain(entity: CommentReportEntity): CommentReport {
        return CommentReport(
            id = entity.id,
            commentId = entity.commentId,
            userId = entity.userId,
            createdAt = entity.baseEntity.createdAt
        )
    }
}
