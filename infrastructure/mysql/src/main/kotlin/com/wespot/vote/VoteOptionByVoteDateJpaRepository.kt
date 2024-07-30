package com.wespot.vote

import org.springframework.data.jpa.repository.JpaRepository

interface VoteOptionByVoteDateJpaRepository : JpaRepository<VoteOptionByVoteDateJpaEntity, Long> {

    fun findAllByVoteId(voteId: Long): List<VoteOptionByVoteDateJpaEntity>

}
