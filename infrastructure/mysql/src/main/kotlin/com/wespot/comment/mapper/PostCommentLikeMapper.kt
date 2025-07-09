package com.wespot.comment.mapper

import com.wespot.comment.PostCommentLike
import com.wespot.comment.PostCommentLikeEntity
import com.wespot.common.BaseEntity
import java.time.LocalDateTime

object PostCommentLikeMapper {

    fun toEntity(commentLike: PostCommentLike): PostCommentLikeEntity {
        return PostCommentLikeEntity(
            id = commentLike.id,
            postCommentId = commentLike.postCommentId,
            userId = commentLike.userId,
            baseEntity = BaseEntity(
                createdAt = commentLike.createdAt,
                updatedAt = LocalDateTime.now()
            )
        )
    }

    fun toDomain(entity: PostCommentLikeEntity): PostCommentLike {
        return PostCommentLike(
            id = entity.id,
            postCommentId = entity.postCommentId,
            userId = entity.userId,
            createdAt = entity.baseEntity.createdAt
        )
    }

}
