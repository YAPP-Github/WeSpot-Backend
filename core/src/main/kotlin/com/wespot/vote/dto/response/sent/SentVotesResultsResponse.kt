package com.wespot.vote.dto.response.sent

import com.wespot.vote.CompleteBallot

data class SentVotesResultsResponse(
    val vote: SingleVoteResponse,
) {

    companion object {

        fun of(ballot: CompleteBallot): SentVotesResultsResponse {
            return SentVotesResultsResponse(SingleVoteResponse.of(ballot.voteOption, ballot.receiver))
        }

    }

}
