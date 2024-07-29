package com.wespot.vote

import com.wespot.voteoption.VoteOption

data class VoteOptionByVoteDate(
    val id: Long,
    val voteId: Long,
    val voteOption: VoteOption
) {

    fun containVoteOptionInVoteOptionByVoteDate(
        voteOptionId: Long
    ): Boolean {
        return voteOptionId == voteOption.id
    }

}
