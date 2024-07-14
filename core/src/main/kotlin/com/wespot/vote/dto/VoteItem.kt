package com.wespot.vote.dto

import com.wespot.user.User
import com.wespot.voteoption.VoteOption

class VoteItem(
    val user: UserResponse,
    val voteOptions: List<VoteOptionResponse>
) {

    companion object {
        fun of(classmate: User, voteOptions: List<VoteOption>): VoteItem {
            return VoteItem(
                user = UserResponse.from(classmate),
                voteOptions = voteOptions.stream()
                    .map { voteOption -> VoteOptionResponse.from(voteOption) }
                    .toList()
            )
        }
    }

}