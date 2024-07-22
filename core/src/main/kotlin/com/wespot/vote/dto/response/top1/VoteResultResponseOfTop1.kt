package com.wespot.vote.dto.response.top1

import com.wespot.vote.VoteRecord
import com.wespot.vote.dto.response.VoteOptionResponse
import com.wespot.voteoption.VoteOption

data class VoteResultResponseOfTop1(
    val voteOption: VoteOptionResponse,
    val voteResult: VoteDetailResultResponseOfTop1?
) {

    companion object {

        fun of(voteOption: VoteOption, voteResults: List<VoteRecord>): VoteResultResponseOfTop1 {
            return VoteResultResponseOfTop1(
                VoteOptionResponse.from(voteOption),
                getVoteDetailResultResponseOfTop1(voteResults)
            )
        }

        private fun getVoteDetailResultResponseOfTop1(voteResults: List<VoteRecord>): VoteDetailResultResponseOfTop1? {
            if (voteResults.isEmpty()) {
                return null
            }

            return VoteDetailResultResponseOfTop1.from(voteResults[0])
        }

    }

}
