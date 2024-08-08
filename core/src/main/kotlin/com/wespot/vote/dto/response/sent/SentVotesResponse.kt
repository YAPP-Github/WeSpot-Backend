package com.wespot.vote.dto.response.sent

import com.wespot.vote.CompleteBallot
import com.wespot.vote.Vote

data class SentVotesResponse(
    val voteId: Long,
    val date: String,
    val sentVoteResults: List<SentVotesResultsResponse>
) {

    companion object {

        fun of(vote: Vote, ballots: List<CompleteBallot>): SentVotesResponse {
            return SentVotesResponse(
                vote.id,
                vote.voteIdentifier.date.toString(),
                ballots.map { SentVotesResultsResponse.of(it) }
            )
        }

    }

}
