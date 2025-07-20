package com.wespot.post.mapper

import com.wespot.common.BaseEntity
import com.wespot.post.PostReport
import com.wespot.post.PostReportEntity
import java.time.LocalDateTime

object PostReportMapper {

    fun toEntity(postReport: PostReport): PostReportEntity {
        return PostReportEntity(
            id = postReport.id,
            postId = postReport.postId,
            userId = postReport.userId,
            reason = postReport.reason,
            baseEntity = BaseEntity(
                createdAt = postReport.createdAt,
                updatedAt = LocalDateTime.now()
            )
        )
    }

    fun toDomain(entity: PostReportEntity): PostReport {
        return PostReport(
            id = entity.id,
            postId = entity.postId,
            userId = entity.userId,
            reason = entity.reason,
            createdAt = entity.baseEntity.createdAt
        )
    }
}
