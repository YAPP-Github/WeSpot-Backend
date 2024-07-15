package com.wespot.vote.dto.response

import com.wespot.voteoption.VoteOption

data class VoteOptionResponse(
    val id: Long,
    val content: String
) {

    companion object {
        fun from(voteOption: VoteOption): VoteOptionResponse {
            return VoteOptionResponse(
                id = voteOption.id!!,
                content = voteOption.content
            )
        }
    }

}