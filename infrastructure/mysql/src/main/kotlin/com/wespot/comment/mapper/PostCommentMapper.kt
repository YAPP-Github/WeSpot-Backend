package com.wespot.comment.mapper

import com.wespot.comment.PostComment
import com.wespot.comment.PostCommentEntity
import com.wespot.comment.PostCommentStatusByViewer
import com.wespot.comment.vo.PostCommentContent
import com.wespot.common.BaseEntity
import com.wespot.user.User
import java.time.LocalDateTime

object PostCommentMapper {

    fun toEntity(postComment: PostComment): PostCommentEntity {
        return PostCommentEntity(
            id = postComment.id,
            postId = postComment.postId,
            userId = postComment.user.id,
            content = postComment.content.content,
            likeCount = postComment.likeCount,
            reportCount = postComment.reportCount,
            baseEntity = BaseEntity(createdAt = postComment.createdAt, updatedAt = LocalDateTime.now())
        )
    }

    fun toDomain(
        entity: PostCommentEntity, user: User, postCommentStatusByViewer: PostCommentStatusByViewer? = null
    ): PostComment {
        return PostComment(
            id = entity.id,
            postId = entity.postId,
            user = user,
            content = PostCommentContent.from(entity.content),
            likeCount = entity.likeCount,
            reportCount = entity.reportCount,
            postCommentStatusByViewer = postCommentStatusByViewer,
            createdAt = entity.baseEntity.createdAt
        )
    }

}
