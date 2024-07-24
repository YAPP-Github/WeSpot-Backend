package com.wespot.vote.dto.response.received

import com.wespot.vote.Vote
import com.wespot.vote.VoteRecord
import com.wespot.voteoption.VoteOption

data class ReceivedVotesResponses(
    val voteData: List<ReceivedVotesResponse>
) {
    companion object {

        fun of(voteResults: Map<Vote, Map<VoteOption, VoteRecord>>): ReceivedVotesResponses {
            val voteData = voteResults
                .map { ReceivedVotesResponse.of(it.key, it.value) }
                .toList()
            return ReceivedVotesResponses(voteData)
        }

    }
}
