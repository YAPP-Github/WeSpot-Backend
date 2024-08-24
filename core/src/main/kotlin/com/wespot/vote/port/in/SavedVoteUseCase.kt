package com.wespot.vote.port.`in`

import com.wespot.vote.dto.request.VoteRequests
import com.wespot.vote.dto.response.SavedVoteResponse
import com.wespot.vote.dto.response.VoteItems

interface SavedVoteUseCase {

    fun getVoteOptions(): VoteItems

    fun saveVote(requests: VoteRequests): SavedVoteResponse

}
