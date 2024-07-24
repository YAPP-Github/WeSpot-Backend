package com.wespot.vote.dto.response.sent

import com.wespot.vote.dto.response.VoteOptionResponse
import com.wespot.voteoption.VoteOption

data class SentVotesResultsResponse(
    val voteOption: VoteOptionResponse,
    val voteCount: Int
) {

    companion object {

        fun of(voteOption: VoteOption, voteCount: Int): SentVotesResultsResponse {
            return SentVotesResultsResponse(
                VoteOptionResponse.from(voteOption),
                voteCount
            )
        }

    }

}
