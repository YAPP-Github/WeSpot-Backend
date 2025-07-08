package com.wespot.post.mapper

import com.wespot.common.BaseEntity
import com.wespot.post.Post
import com.wespot.post.PostCategory
import com.wespot.post.PostEntity
import com.wespot.post.vo.PostDescription
import com.wespot.post.vo.PostTitle
import java.time.LocalDateTime

object PostMapper {

    fun toEntity(domain: Post): PostEntity {
        return PostEntity(
            id = domain.id,
            categoryId = domain.category.id,
            title = domain.title?.content,
            description = domain.description.content,
            likeCount = domain.likeCount,
            commentCount = domain.commentCount,
            baseEntity = BaseEntity(domain.createdAt, LocalDateTime.now()),
        )
    }

    fun toDomain(entity: PostEntity, postCategory: PostCategory): Post {
        return Post(
            id = entity.id,
            category = postCategory,
            title = entity.title?.let { PostTitle(it) },
            description = PostDescription(entity.description),
            likeCount = entity.likeCount,
            commentCount = entity.commentCount,
            createdAt = entity.baseEntity.createdAt
        )
    }
}
