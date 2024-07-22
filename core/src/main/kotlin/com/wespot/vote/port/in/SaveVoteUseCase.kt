package com.wespot.vote.port.`in`

import com.wespot.vote.dto.request.VoteRequests
import com.wespot.vote.dto.response.SaveVoteResponse
import com.wespot.vote.dto.response.VoteItems

interface SaveVoteUseCase {

    fun getVoteOptions(userId: Long): VoteItems

    fun saveVote(userId: Long, requests: VoteRequests): SaveVoteResponse

}
