package com.wespot.post.mapper

import com.wespot.common.BaseEntity
import com.wespot.post.PostLike
import com.wespot.post.PostLikeEntity
import java.time.LocalDateTime

object PostLikeMapper {

    fun toEntity(postLike: PostLike): PostLikeEntity {
        return PostLikeEntity(
            id = postLike.id,
            postId = postLike.postId,
            userId = postLike.userId,
            baseEntity = BaseEntity(
                createdAt = postLike.createdAt,
                updatedAt = LocalDateTime.now()
            )
        )
    }

    fun toDomain(entity: PostLikeEntity): PostLike {
        return PostLike(
            id = entity.id,
            postId = entity.postId,
            userId = entity.userId,
            createdAt = entity.baseEntity.createdAt
        )
    }

}
