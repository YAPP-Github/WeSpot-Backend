package com.wespot.vote.dto.response.top1

import com.wespot.vote.VoteRecord
import com.wespot.voteoption.VoteOption

data class VoteResultResponsesOfTop1(
    val voteResults: List<VoteResultResponseOfTop1>
) {

    companion object {

        fun from(voteResults: Map<VoteOption, List<VoteRecord>>): VoteResultResponsesOfTop1 {
            val results = voteResults.map { VoteResultResponseOfTop1.of(it.key, it.value) }
                .toList()
            return VoteResultResponsesOfTop1(results)
        }

    }

}
