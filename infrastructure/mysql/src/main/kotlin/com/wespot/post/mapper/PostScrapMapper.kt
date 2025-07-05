package com.wespot.post.mapper

import com.wespot.common.BaseEntity
import com.wespot.post.PostScrap
import com.wespot.post.PostScrapEntity
import java.time.LocalDateTime

object PostScrapMapper {

    fun toEntity(postScrap: PostScrap): PostScrapEntity {
        return PostScrapEntity(
            id = postScrap.id,
            postId = postScrap.postId,
            userId = postScrap.userId,
            baseEntity = BaseEntity(
                createdAt = postScrap.createdAt,
                updatedAt = LocalDateTime.now()
            )
        )
    }

    fun toDomain(entity: PostScrapEntity): PostScrap {
        return PostScrap(
            id = entity.id,
            postId = entity.postId,
            userId = entity.userId,
            createdAt = entity.baseEntity.createdAt
        )
    }

}
