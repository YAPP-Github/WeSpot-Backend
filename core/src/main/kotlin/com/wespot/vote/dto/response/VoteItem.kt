package com.wespot.vote.dto.response

import com.wespot.user.User
import com.wespot.voteoption.VoteOption

data class VoteItem(
    val user: VoteUserResponse,
    val voteOptions: List<VoteOptionResponse>
) {

    companion object {

        fun of(classmate: User, voteOptions: List<VoteOption>): VoteItem {
            return VoteItem(
                user = VoteUserResponse.from(classmate),
                voteOptions = voteOptions.stream()
                    .map { voteOption -> VoteOptionResponse.from(voteOption) }
                    .toList()
            )
        }

    }

}
