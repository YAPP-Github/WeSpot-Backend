package com.wespot.vote.dto.response.sent

import com.wespot.vote.Ballot
import com.wespot.vote.Vote
import com.wespot.voteoption.VoteOption

data class SentVotesResponses(
    val voteData: List<SentVotesResponse>
) {
    companion object {

        fun from(voteResults: Map<Vote, Map<VoteOption, List<Ballot>>>): SentVotesResponses {
            val voteData = voteResults
                .map { SentVotesResponse.of(it.key, it.value) }
                .toList()
            return SentVotesResponses(voteData)
        }

    }
}
