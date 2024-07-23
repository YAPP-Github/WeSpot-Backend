package com.wespot.vote.dto.response.received

import com.wespot.vote.VoteRecord
import com.wespot.voteoption.VoteOption

data class ReceivedVoteResponse(
    val voteResult: ReceivedVoteResultResponse
) {
    companion object {

        fun of(rate: Int, voteOption: VoteOption, voteRecord: VoteRecord): ReceivedVoteResponse {
            return ReceivedVoteResponse(
                ReceivedVoteResultResponse.of(rate, voteOption, voteRecord)
            )
        }

    }
}
