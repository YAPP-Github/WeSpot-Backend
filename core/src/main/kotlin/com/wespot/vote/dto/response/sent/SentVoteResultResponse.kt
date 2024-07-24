package com.wespot.vote.dto.response.sent

import com.wespot.user.User
import com.wespot.vote.dto.response.VoteOptionResponse
import com.wespot.vote.dto.response.VoteUserResponse
import com.wespot.voteoption.VoteOption

data class SentVoteResultResponse(
    val voteOption: VoteOptionResponse,
    val voteUsers: List<VoteUserResponse>
) {

    companion object {

        fun of(voteOption: VoteOption, voteUsers: List<User>): SentVoteResultResponse {
            return SentVoteResultResponse(
                VoteOptionResponse.from(voteOption),
                voteUsers.map { VoteUserResponse.from(it) }
            )
        }

    }
}
