package com.wespot.vote

import com.wespot.user.User
import com.wespot.voteoption.VoteOption
import java.time.LocalDateTime

data class Ballot(
    val id: Long,
    val voteOption: VoteOption,
    val sender: User,
    val receiver: User,
    val createdAt: LocalDateTime,
    val isReceiverRead: Boolean,
) {
}