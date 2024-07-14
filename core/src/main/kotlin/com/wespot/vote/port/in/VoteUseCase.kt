package com.wespot.vote.port.`in`

import com.wespot.vote.dto.VoteItems
import org.springframework.stereotype.Service

@Service
interface VoteUseCase {

    fun getVoteOptions(userId: Long): VoteItems

    fun saveVote(userId: Long): Long

}