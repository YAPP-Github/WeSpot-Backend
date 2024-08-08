package com.wespot.vote.dto.response.sent

import com.wespot.vote.CompleteBallot
import com.wespot.vote.Vote

data class SentVotesResponses(
    val voteData: List<SentVotesResponse>,
    val hasNext: Boolean
) {
    companion object {

        fun from(voteResult: Map<Vote, List<CompleteBallot>>, hasNext: Boolean): SentVotesResponses {
            val voteData = voteResult
                .map { SentVotesResponse.of(it.key, it.value) }
                .toList()
            return SentVotesResponses(voteData, hasNext)
        }

    }
}
