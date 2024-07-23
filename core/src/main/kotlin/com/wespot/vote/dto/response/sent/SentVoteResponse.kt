package com.wespot.vote.dto.response.sent

import com.wespot.user.User
import com.wespot.voteoption.VoteOption

data class SentVoteResponse(
    val voteResult: SentVoteResultResponse
) {

    companion object {

        fun of(voteOption: VoteOption, users: List<User>): SentVoteResponse {
            return SentVoteResponse(SentVoteResultResponse.of(voteOption, users));
        }

    }

}
