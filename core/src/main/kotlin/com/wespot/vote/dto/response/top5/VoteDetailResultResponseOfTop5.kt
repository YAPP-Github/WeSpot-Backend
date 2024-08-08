package com.wespot.vote.dto.response.top5

import com.wespot.vote.VoteRecord
import com.wespot.vote.dto.response.VoteUserResponse

data class VoteDetailResultResponseOfTop5(
    val user: VoteUserResponse,
    val voteCount: Int
) {

    companion object {

        fun from(voteRecords: VoteRecord): VoteDetailResultResponseOfTop5 {
            return VoteDetailResultResponseOfTop5(
                VoteUserResponse.from(voteRecords.user),
                voteRecords.voteCount
            )
        }

    }

}
