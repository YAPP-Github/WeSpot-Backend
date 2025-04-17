package com.wespot.user.mapper

import com.wespot.common.BaseEntity
import com.wespot.user.User
import com.wespot.user.entity.message.AnonymousProfileJpaEntity
import com.wespot.user.message.AnonymousProfile

object AnonymousProfileMapper {

    fun mapToDomainEntity(anonymousProfileJpaEntity: AnonymousProfileJpaEntity, owner: User): AnonymousProfile =
        AnonymousProfile(
            id = anonymousProfileJpaEntity.id,
            imageUrl = anonymousProfileJpaEntity.imageUrl,
            name = anonymousProfileJpaEntity.name,
            createdAt = anonymousProfileJpaEntity.baseEntity.createdAt,
            updatedAt = anonymousProfileJpaEntity.baseEntity.updatedAt,
            ownerId = owner,
            receiverId = anonymousProfileJpaEntity.receiverId
        )

    fun mapToJpaEntity(anonymousProfile: AnonymousProfile): AnonymousProfileJpaEntity = AnonymousProfileJpaEntity(
        id = anonymousProfile.id,
        imageUrl = anonymousProfile.imageUrl,
        name = anonymousProfile.name,
        baseEntity = BaseEntity(anonymousProfile.createdAt, anonymousProfile.updatedAt),
        ownerId = anonymousProfile.ownerId.id,
        receiverId = anonymousProfile.receiverId
    )

}
