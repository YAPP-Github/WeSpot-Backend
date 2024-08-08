package com.wespot.vote.dto.response.received

import com.wespot.vote.Vote
import com.wespot.vote.VoteRecord
import com.wespot.voteoption.VoteOption

data class ReceivedVotesResponse(
    val voteId: Long,
    val date: String,
    val receivedVoteResults: List<ReceivedVotesResultResponses>
) {

    companion object {

        fun of(vote: Vote, voteResults: Map<VoteOption, VoteRecord>): ReceivedVotesResponse {
            return ReceivedVotesResponse(
                vote.id,
                vote.voteIdentifier.date.toString(),
                voteResults.map { ReceivedVotesResultResponses.of(it.key, it.value) }
                    .toList()
            )
        }

    }

}
