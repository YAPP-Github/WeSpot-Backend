package com.wespot.voteoption

import com.wespot.common.BaseEntity

object VoteOptionMapper {

    fun mapToDomainEntity(voteOptionJpaEntity: VoteOptionJpaEntity): VoteOption =
        VoteOption(
            id = voteOptionJpaEntity.id,
            content = VoteOptionContent.from(voteOptionJpaEntity.content),
            createdAt = voteOptionJpaEntity.baseEntity.createdAt,
            updatedAt = voteOptionJpaEntity.baseEntity.updatedAt
        )

    fun mapToJpaEntity(voteOption: VoteOption): VoteOptionJpaEntity =
        VoteOptionJpaEntity(
            id = voteOption.id,
            content = voteOption.content.content,
            baseEntity = BaseEntity(
                createdAt = voteOption.createdAt,
                updatedAt = voteOption.updatedAt
            )
        )

}
