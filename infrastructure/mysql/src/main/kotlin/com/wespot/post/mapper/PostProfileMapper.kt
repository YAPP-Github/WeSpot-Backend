package com.wespot.post.mapper

import com.wespot.common.BaseEntity
import com.wespot.post.PostProfile
import com.wespot.post.PostProfileEntity
import java.time.LocalDateTime

object PostProfileMapper {

    fun toEntity(postProfile: PostProfile): PostProfileEntity {
        return PostProfileEntity(
            id = postProfile.id,
            userId = postProfile.userId,
            url = postProfile.url,
            name = postProfile.name,
            baseEntity = BaseEntity(createdAt = postProfile.createdAt, updatedAt = LocalDateTime.now())
        )
    }

    fun toDomain(entity: PostProfileEntity): PostProfile {
        return PostProfile(
            id = entity.id,
            userId = entity.userId,
            url = entity.url,
            name = entity.name,
            createdAt = entity.baseEntity.createdAt
        )
    }

}
