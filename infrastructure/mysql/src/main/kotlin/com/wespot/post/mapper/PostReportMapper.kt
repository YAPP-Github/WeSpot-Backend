package com.wespot.post.mapper

import com.wespot.common.BaseEntity
import com.wespot.post.PostReport
import com.wespot.post.PostReportEntity
import com.wespot.post.PostReportReason
import java.time.LocalDateTime

object PostReportMapper {

    fun toEntity(postReport: PostReport): PostReportEntity {
        return PostReportEntity(
            id = postReport.id,
            postId = postReport.postId,
            userId = postReport.userId,
            baseEntity = BaseEntity(
                createdAt = postReport.createdAt,
                updatedAt = LocalDateTime.now()
            )
        )
    }

    fun toDomain(entity: PostReportEntity, postReportReasons: List<PostReportReason>): PostReport {
        return PostReport(
            id = entity.id,
            postId = entity.postId,
            userId = entity.userId,
            postReportReasons = postReportReasons,
            createdAt = entity.baseEntity.createdAt
        )
    }
}
