package com.wespot.vote.dto.response.top1

import com.wespot.vote.VoteRecord

data class VoteDetailResultResponseOfTop1(
    val user: VoteUserResponseOfTop1,
    val voteCount: Int
) {

    companion object {

        fun from(voteRecords: VoteRecord): VoteDetailResultResponseOfTop1 {
            return VoteDetailResultResponseOfTop1(
                VoteUserResponseOfTop1.from(voteRecords.user),
                voteRecords.voteCount
            )
        }

    }

}
