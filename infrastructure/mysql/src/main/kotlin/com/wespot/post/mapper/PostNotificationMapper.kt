package com.wespot.post.mapper

import com.wespot.common.BaseEntity
import com.wespot.post.PostNotification
import com.wespot.post.PostNotificationEntity
import com.wespot.user.User
import java.time.LocalDateTime

object PostNotificationMapper {

    fun toDomain(
        entity: PostNotificationEntity,
        user: User,
    ): PostNotification =
        PostNotification(
            id = entity.id,
            postId = entity.postId,
            user = user,
            createdAt = entity.baseEntity.createdAt
        )

    fun toEntity(
        postCommentNotification: PostNotification
    ): PostNotificationEntity =
        PostNotificationEntity(
            id = postCommentNotification.id,
            postId = postCommentNotification.postId,
            userId = postCommentNotification.user.id,
            baseEntity = BaseEntity(
                createdAt = postCommentNotification.createdAt,
                updatedAt = LocalDateTime.now()
            )
        )

}
