package com.wespot.vote

import com.wespot.common.BaseEntity

object BallotMapper {

    fun mapToDomainEntity(ballotJpaEntity: BallotJpaEntity): Ballot =
        Ballot(
            id = ballotJpaEntity.id,
            voteId = ballotJpaEntity.voteId,
            voteOptionId = ballotJpaEntity.voteOptionId,
            senderId = ballotJpaEntity.senderId,
            receiverId = ballotJpaEntity.receiverId,
            createdAt = ballotJpaEntity.baseEntity.createdAt,
            updatedAt = ballotJpaEntity.baseEntity.updatedAt,
            isReceiverRead = ballotJpaEntity.isReceiverRead
        )

    fun mapToJpaEntity(ballot: Ballot): BallotJpaEntity =
        BallotJpaEntity(
            id = ballot.id,
            voteId = ballot.voteId,
            voteOptionId = ballot.voteOptionId,
            senderId = ballot.senderId,
            receiverId = ballot.receiverId,
            baseEntity = BaseEntity(
                createdAt = ballot.createdAt,
                updatedAt = ballot.updatedAt,
            ),
            isReceiverRead = ballot.isReceiverRead
        )

}
