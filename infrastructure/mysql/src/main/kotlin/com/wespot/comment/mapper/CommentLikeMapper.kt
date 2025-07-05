package com.wespot.comment.mapper

import com.wespot.comment.CommentLike
import com.wespot.comment.CommentLikeEntity
import com.wespot.common.BaseEntity
import java.time.LocalDateTime

object CommentLikeMapper {

    fun toEntity(commentLike: CommentLike): CommentLikeEntity {
        return CommentLikeEntity(
            id = commentLike.id,
            commentId = commentLike.commentId,
            userId = commentLike.userId,
            baseEntity = BaseEntity(
                createdAt = commentLike.createdAt,
                updatedAt = LocalDateTime.now()
            )
        )
    }

    fun toDomain(entity: CommentLikeEntity): CommentLike {
        return CommentLike(
            id = entity.id,
            commentId = entity.commentId,
            userId = entity.userId,
            createdAt = entity.baseEntity.createdAt
        )
    }

}
