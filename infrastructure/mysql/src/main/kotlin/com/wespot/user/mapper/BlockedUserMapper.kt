package com.wespot.user.mapper

import com.wespot.user.block.BlockedUser
import com.wespot.user.entity.BlockedUserJpaEntity

object BlockedUserMapper {
    fun mapToDomainEntity(blockUserJpaEntity: BlockedUserJpaEntity): BlockedUser =
        BlockedUser(
            id = blockUserJpaEntity.id,
            blockerId = blockUserJpaEntity.blockedId,
            blockedId = blockUserJpaEntity.blockedId,
            createdAt = blockUserJpaEntity.createdAt
        )

    fun mapToJpaEntity(blockedUser: BlockedUser): BlockedUserJpaEntity =
        BlockedUserJpaEntity(
            id = blockedUser.id,
            blockerId = blockedUser.blockerId,
            blockedId = blockedUser.blockedId,
            createdAt = blockedUser.createdAt
        )
}
