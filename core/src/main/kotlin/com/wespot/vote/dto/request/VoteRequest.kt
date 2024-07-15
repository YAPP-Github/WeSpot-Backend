package com.wespot.vote.dto.request

data class VoteRequest(
    val userId: Long,
    val voteOptionId: Long
) {
}
