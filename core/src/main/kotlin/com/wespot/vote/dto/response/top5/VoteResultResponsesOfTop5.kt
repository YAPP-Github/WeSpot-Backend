package com.wespot.vote.dto.response.top5

import com.wespot.voteoption.VoteOption

data class VoteResultResponsesOfTop5(
    val voteResults: List<VoteResultResponseOfTop5>
) {

    companion object {

        fun from(voteResults: Map<VoteOption, List<VoteRecord>>): VoteResultResponsesOfTop5 {
            val results = voteResults.map { VoteResultResponseOfTop5.of(it.key, it.value) }
                .toList()
            return VoteResultResponsesOfTop5(results)
        }

    }

}
