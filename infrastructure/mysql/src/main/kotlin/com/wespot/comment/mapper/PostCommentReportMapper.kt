package com.wespot.comment.mapper

import com.wespot.comment.PostCommentReport
import com.wespot.comment.PostCommentReportEntity
import com.wespot.common.BaseEntity
import java.time.LocalDateTime

object PostCommentReportMapper {

    fun toEntity(postCommentReport: PostCommentReport): PostCommentReportEntity {
        return PostCommentReportEntity(
            id = postCommentReport.id,
            postCommentId = postCommentReport.postCommentId,
            userId = postCommentReport.userId,
            baseEntity = BaseEntity(createdAt = postCommentReport.createdAt, updatedAt = LocalDateTime.now())
        )
    }

    fun toDomain(entity: PostCommentReportEntity): PostCommentReport {
        return PostCommentReport(
            id = entity.id,
            postCommentId = entity.postCommentId,
            userId = entity.userId,
            createdAt = entity.baseEntity.createdAt
        )
    }
}
