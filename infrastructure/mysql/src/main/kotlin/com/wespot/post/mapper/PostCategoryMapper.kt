package com.wespot.post.mapper

import com.wespot.common.BaseEntity
import com.wespot.post.PostCategory
import com.wespot.post.PostCategoryEntity
import java.time.LocalDateTime

object PostCategoryMapper {

    fun toEntity(postCategory: PostCategory): PostCategoryEntity {
        return PostCategoryEntity(
            id = postCategory.id,
            majorCategoryName = postCategory.majorCategoryName,
            name = postCategory.name,
            thumbnail = postCategory.thumbnail,
            backgroundImage = postCategory.backgroundImage,
            baseEntity = BaseEntity(createdAt = postCategory.createdAt, updatedAt = LocalDateTime.now())
        )
    }

    fun toDomain(entity: PostCategoryEntity): PostCategory {
        return PostCategory(
            id = entity.id,
            majorCategoryName = entity.majorCategoryName,
            name = entity.name,
            thumbnail = entity.thumbnail,
            backgroundImage = entity.backgroundImage,
            createdAt = entity.baseEntity.createdAt
        )
    }

}
