package com.wespot.vote.port.`in`

import org.springframework.stereotype.Service

@Service
interface VoteUseCase {

    fun getVoteOptions(userId: Long, voteOptionId: Long)

    fun saveVote(userId: Long, voteOptionId: Long)

}