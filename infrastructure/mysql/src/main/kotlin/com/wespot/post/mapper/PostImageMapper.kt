package com.wespot.post.mapper

import com.wespot.common.BaseEntity
import com.wespot.post.PostImage
import com.wespot.post.PostImageEntity
import java.time.LocalDateTime

object PostImageMapper {

    fun toEntity(domain: PostImage): PostImageEntity {
        return PostImageEntity(
            id = domain.id,
            postId = domain.postId,
            url = domain.url,
            baseEntity = BaseEntity(domain.createdAt, LocalDateTime.now())
        )
    }

    fun toDomain(entity: PostImageEntity): PostImage {
        return PostImage(
            id = entity.id,
            postId = entity.postId,
            url = entity.url,
            createdAt = entity.baseEntity.createdAt
        )
    }
}
