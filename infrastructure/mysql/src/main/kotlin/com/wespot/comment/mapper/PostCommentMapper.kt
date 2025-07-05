package com.wespot.comment.mapper

import com.wespot.comment.PostComment
import com.wespot.comment.PostCommentEntity
import com.wespot.common.BaseEntity
import java.time.LocalDateTime

object PostCommentMapper {

    fun toEntity(postComment: PostComment): PostCommentEntity {
        return PostCommentEntity(
            id = postComment.id,
            postId = postComment.postId,
            userId = postComment.userId,
            content = postComment.content,
            likeCount = postComment.likeCount,
            reportCount = postComment.reportCount,
            baseEntity = BaseEntity(createdAt = postComment.createdAt, updatedAt = LocalDateTime.now())
        )
    }

    fun toDomain(entity: PostCommentEntity): PostComment {
        return PostComment(
            id = entity.id,
            postId = entity.postId,
            userId = entity.userId,
            content = entity.content,
            likeCount = entity.likeCount,
            reportCount = entity.reportCount,
            createdAt = entity.baseEntity.createdAt
        )
    }

}
