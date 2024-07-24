package com.wespot.vote.dto.response.sent

import com.wespot.vote.Ballot
import com.wespot.vote.Vote
import com.wespot.voteoption.VoteOption

data class SentVotesResponse(
    val date: String,
    val sentVoteResults: List<SentVotesResultsResponse>
) {

    companion object {

        fun of(vote: Vote, voteResults: Map<VoteOption, List<Ballot>>): SentVotesResponse {
            return SentVotesResponse(
                vote.date.toString(),
                voteResults.map { SentVotesResultsResponse.of(it.key, it.value.size) }
                    .toList()
            )
        }

    }

}
