package com.wespot.post.mapper

import com.wespot.common.BaseEntity
import com.wespot.post.Post
import com.wespot.post.PostEntity
import java.time.LocalDateTime

object PostMapper {

    fun toEntity(domain: Post): PostEntity {
        return PostEntity(
            id = domain.id,
            category_id = domain.category_id,
            title = domain.title,
            description = domain.description,
            likeCount = domain.likeCount,
            commentCount = domain.commentCount,
            baseEntity = BaseEntity(domain.createdAt, LocalDateTime.now()),
        )
    }

    fun toDomain(entity: PostEntity): Post {
        return Post(
            id = entity.id,
            category_id = entity.category_id,
            title = entity.title,
            description = entity.description,
            likeCount = entity.likeCount,
            commentCount = entity.commentCount,
            createdAt = entity.baseEntity.createdAt
        )
    }
}
