package com.wespot.vote.dto.response.top5

import com.wespot.vote.VoteRecord

data class VoteDetailResultResponseOfTop5(
    val user: VoteUserResponseOfTop5,
    val voteCount: Int
) {

    companion object {

        fun from(voteRecords: VoteRecord): VoteDetailResultResponseOfTop5 {
            return VoteDetailResultResponseOfTop5(
                VoteUserResponseOfTop5.from(voteRecords.user),
                voteRecords.voteCount
            )
        }

    }

}
