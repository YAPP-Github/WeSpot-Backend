package com.wespot.vote.port.`in`

import com.wespot.vote.dto.VoteOptionResponses
import org.springframework.stereotype.Service

@Service
interface VoteUseCase {

    fun getVoteOptions(userId: Long): VoteOptionResponses

    fun saveVote(userId: Long): Long

}