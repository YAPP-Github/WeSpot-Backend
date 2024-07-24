package com.wespot.vote.dto.response.received

import com.wespot.vote.VoteRecord
import com.wespot.vote.dto.response.VoteOptionResponse
import com.wespot.vote.dto.response.VoteUserResponse
import com.wespot.voteoption.VoteOption

data class ReceivedVoteResultResponse(
    val voteOption: VoteOptionResponse,
    val user: VoteUserResponse,
    val rate: Int,
    val voteCount: Int
) {

    companion object {

        fun of(
            rate: Int,
            voteOption: VoteOption,
            voteRecord: VoteRecord
        ): ReceivedVoteResultResponse {
            return ReceivedVoteResultResponse(
                VoteOptionResponse.from(voteOption),
                VoteUserResponse.from(voteRecord.user),
                rate,
                voteRecord.voteCount
            )
        }

    }

}
