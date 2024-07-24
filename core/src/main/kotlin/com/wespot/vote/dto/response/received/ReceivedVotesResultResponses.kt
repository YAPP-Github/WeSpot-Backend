package com.wespot.vote.dto.response.received

import com.wespot.vote.VoteRecord
import com.wespot.vote.dto.response.VoteOptionResponse
import com.wespot.voteoption.VoteOption

data class ReceivedVotesResultResponses(
    val voteOption: VoteOptionResponse,
    val voteCount: Int,
    val isNew: Boolean
) {

    companion object {

        fun of(
            voteOption: VoteOption,
            voteRecord: VoteRecord
        ): ReceivedVotesResultResponses {
            return ReceivedVotesResultResponses(
                VoteOptionResponse.from(voteOption),
                voteRecord.voteCount,
                !voteRecord.isReceiverRead
            )
        }

    }
}
