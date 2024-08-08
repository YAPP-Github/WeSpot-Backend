package com.wespot.vote.dto.response.sent

import com.wespot.user.User
import com.wespot.vote.dto.response.VoteOptionResponse
import com.wespot.vote.dto.response.VoteUserResponse
import com.wespot.voteoption.VoteOption

data class SingleVoteResponse(
    val voteOption: VoteOptionResponse,
    val user: VoteUserResponse
) {
    companion object {
        fun of(voteOption: VoteOption, user: User): SingleVoteResponse {
            return SingleVoteResponse(
                VoteOptionResponse.from(voteOption),
                VoteUserResponse.from(user)
            )
        }
    }
}
