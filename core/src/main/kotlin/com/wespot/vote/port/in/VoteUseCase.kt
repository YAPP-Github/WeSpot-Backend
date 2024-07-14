package com.wespot.vote.port.`in`

import com.wespot.vote.dto.request.VoteRequest
import com.wespot.vote.dto.response.VoteItems

interface VoteUseCase {

    fun getVoteOptions(userId: Long): VoteItems

    fun saveVote(userId: Long, requests: List<VoteRequest>): Long

}