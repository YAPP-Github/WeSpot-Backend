package com.wespot.vote

import org.springframework.data.jpa.repository.JpaRepository

interface BallotJpaRepository : JpaRepository<BallotJpaEntity, Long> {

    fun findAllByVoteId(voteId: Long): List<BallotJpaEntity>

    fun deleteByReceiverIdOrSenderId(receiverId: Long, senderId: Long)

}
