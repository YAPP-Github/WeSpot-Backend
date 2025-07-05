package com.wespot.post.mapper

import com.wespot.common.BaseEntity
import com.wespot.post.PostBlock
import com.wespot.post.PostBlockEntity
import java.time.LocalDateTime

object PostBlockMapper {

    fun toEntity(postBlock: PostBlock): PostBlockEntity {
        return PostBlockEntity(
            id = postBlock.id,
            postId = postBlock.postId,
            userId = postBlock.userId,
            baseEntity = BaseEntity(
                createdAt = postBlock.createdAt,
                updatedAt = LocalDateTime.now()
            )
        )
    }

    fun toDomain(entity: PostBlockEntity): PostBlock {
        return PostBlock(
            id = entity.id,
            postId = entity.postId,
            userId = entity.userId,
            createdAt = entity.baseEntity.createdAt
        )
    }

}
