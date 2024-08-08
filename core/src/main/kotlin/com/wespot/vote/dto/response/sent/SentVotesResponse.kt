package com.wespot.vote.dto.response.sent

import com.wespot.vote.CompleteBallot
import com.wespot.vote.Vote

data class SentVotesResponse(
    val date: String,
    val sentVoteResults: List<SentVotesResultsResponse>
) {

    companion object {

        fun of(vote: Vote, ballots: List<CompleteBallot>): SentVotesResponse {
            return SentVotesResponse(
                vote.voteIdentifier.date.toString(),
                ballots.map { SentVotesResultsResponse.of(it) }
            )
        }

    }

}
