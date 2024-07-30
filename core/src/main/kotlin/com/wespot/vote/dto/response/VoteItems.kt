package com.wespot.vote.dto.response

import com.wespot.user.User
import com.wespot.vote.VoteOptionsByVoteDate
import com.wespot.voteoption.VoteOption

data class VoteItems(
    val voteItems: List<VoteItem>
) {

    companion object {

        fun of(classmates: List<User>, voteOptionsByVoteDate: VoteOptionsByVoteDate): VoteItems {
            return VoteItems(
                voteItems = classmates.stream()
                    .map { classmate -> VoteItem.of(classmate, getVoteOptions(voteOptionsByVoteDate)) }.toList()
            )
        }

        private fun getVoteOptions(voteOptionsByVoteDate: VoteOptionsByVoteDate): List<VoteOption> {
            return voteOptionsByVoteDate.voteOptionsByVoteDate
                .map { it.voteOption }
        }

    }

}
