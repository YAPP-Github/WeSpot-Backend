package com.wespot.post.mapper

import com.wespot.common.BaseEntity
import com.wespot.post.Post
import com.wespot.post.PostCategory
import com.wespot.post.PostEntity
import com.wespot.post.PostImages
import com.wespot.post.vo.PostDescription
import com.wespot.post.vo.PostTitle
import com.wespot.user.User
import java.time.LocalDateTime

object PostMapper {

    fun toEntity(domain: Post): PostEntity {
        return PostEntity(
            id = domain.id,
            categoryId = domain.category.id,
            userId = domain.user.id,
            title = domain.title?.content,
            description = domain.description.content,
            likeCount = domain.likeCount,
            commentCount = domain.commentCount,
            baseEntity = BaseEntity(domain.createdAt, LocalDateTime.now()),
        )
    }

    fun toDomain(entity: PostEntity, postCategory: PostCategory, user: User, postImages: PostImages?): Post {
        return Post(
            id = entity.id,
            category = postCategory,
            user = user,
            title = entity.title?.let { PostTitle(it) },
            description = PostDescription(entity.description),
            likeCount = entity.likeCount,
            commentCount = entity.commentCount,
            images = postImages,
            createdAt = entity.baseEntity.createdAt
        )
    }
}
