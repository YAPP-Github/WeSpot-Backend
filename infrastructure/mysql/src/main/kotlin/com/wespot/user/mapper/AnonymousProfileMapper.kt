package com.wespot.user.mapper

import com.wespot.common.BaseEntity
import com.wespot.user.User
import com.wespot.user.entity.UserJpaEntity
import com.wespot.user.entity.message.AnonymousProfileJpaEntity
import com.wespot.user.message.AnonymousProfile

object AnonymousProfileMapper {

    fun mapToDomainEntity(
        anonymousProfileJpaEntity: AnonymousProfileJpaEntity,
        owner: UserJpaEntity,
        receiver: UserJpaEntity
    ): AnonymousProfile {
        val ownerDomainEntity = UserMapper.mapToDomainEntity(owner)
        val receiverDomainEntity = UserMapper.mapToDomainEntity(receiver)

        return AnonymousProfile(
            id = anonymousProfileJpaEntity.id,
            imageUrl = anonymousProfileJpaEntity.imageUrl,
            name = anonymousProfileJpaEntity.name,
            createdAt = anonymousProfileJpaEntity.baseEntity.createdAt,
            updatedAt = anonymousProfileJpaEntity.baseEntity.updatedAt,
            owner = ownerDomainEntity,
            receiver = receiverDomainEntity,
        )
    }

    fun mapToDomainEntity(
        anonymousProfileJpaEntity: AnonymousProfileJpaEntity,
        owner: User,
        receiver: User
    ): AnonymousProfile {
        return AnonymousProfile(
            id = anonymousProfileJpaEntity.id,
            imageUrl = anonymousProfileJpaEntity.imageUrl,
            name = anonymousProfileJpaEntity.name,
            createdAt = anonymousProfileJpaEntity.baseEntity.createdAt,
            updatedAt = anonymousProfileJpaEntity.baseEntity.updatedAt,
            owner = owner,
            receiver = receiver,
        )
    }


    fun mapToJpaEntity(anonymousProfile: AnonymousProfile): AnonymousProfileJpaEntity = AnonymousProfileJpaEntity(
        id = anonymousProfile.id,
        imageUrl = anonymousProfile.imageUrl,
        name = anonymousProfile.name,
        baseEntity = BaseEntity(anonymousProfile.createdAt, anonymousProfile.updatedAt),
        ownerId = anonymousProfile.owner.id,
        receiverId = anonymousProfile.receiver.id
    )

}
