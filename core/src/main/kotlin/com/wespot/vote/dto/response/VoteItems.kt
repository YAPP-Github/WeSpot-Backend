package com.wespot.vote.dto.response

import com.wespot.user.User
import com.wespot.voteoption.VoteOption

data class VoteItems(
    val voteItems: List<VoteItem>
) {
    companion object {

        fun of(classmates: List<User>, voteOptions: List<VoteOption>): VoteItems {
            return VoteItems(
                voteItems = classmates.stream()
                    .map { classmate -> VoteItem.of(classmate, voteOptions) }.toList()
            )
        }

    }
}