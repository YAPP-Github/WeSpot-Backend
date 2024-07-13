package com.wespot.vote.dto

import com.wespot.voteoption.VoteOption

class VoteOptionResponse(
    val user: UserResponse,
    val voteOptions: List<VoteOption>
) {
}