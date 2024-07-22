package com.wespot.vote.dto.response.top5

import com.wespot.vote.VoteRecord
import com.wespot.vote.dto.response.VoteOptionResponse
import com.wespot.voteoption.VoteOption

data class VoteResultResponseOfTop5(
    val voteOption: VoteOptionResponse,
    val voteResults: List<VoteDetailResultResponseOfTop5>
) {

    companion object {

        fun of(voteOption: VoteOption, voteResults: List<VoteRecord>): VoteResultResponseOfTop5 {
            return VoteResultResponseOfTop5(
                VoteOptionResponse.from(voteOption),
                voteResults.stream()
                    .map { VoteDetailResultResponseOfTop5.from(it) }
                    .toList()
            )
        }

    }

}
