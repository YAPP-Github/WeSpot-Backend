package com.wespot.vote

import com.wespot.voteoption.VoteOption

object VoteOptionByVoteDateMapper {

    fun mapToJpaEntity(voteOptionByVoteDate: VoteOptionByVoteDate): VoteOptionByVoteDateJpaEntity {
        return VoteOptionByVoteDateJpaEntity(
            id = voteOptionByVoteDate.id,
            voteId = voteOptionByVoteDate.voteId,
            voteOptionId = voteOptionByVoteDate.voteOption.id
        )
    }

    fun mapToDomainEntity(
        voteOptionByVoteDateJpaEntity: VoteOptionByVoteDateJpaEntity,
        voteOption: VoteOption
    ): VoteOptionByVoteDate {
        return VoteOptionByVoteDate(
            id = voteOptionByVoteDateJpaEntity.id,
            voteId = voteOptionByVoteDateJpaEntity.voteId,
            voteOption = voteOption
        )
    }

}
