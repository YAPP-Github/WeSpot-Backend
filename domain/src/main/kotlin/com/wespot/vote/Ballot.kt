package com.wespot.vote

import com.wespot.user.User
import com.wespot.voteoption.VoteOption
import java.time.LocalDateTime
import java.util.*

data class Ballot(
    val id: Long?,
    val voteOption: VoteOption,
    val sender: User,
    val receiver: User,
    val createdAt: LocalDateTime?,
    val isReceiverRead: Boolean,
) {

    companion object {
        fun of(voteOption: VoteOption, sender: User, receiver: User): Ballot {
            if (sender == receiver) {
                throw IllegalArgumentException("본인을 투표할 수 없습니다.")
            }
            return Ballot(
                id = null,
                voteOption = voteOption,
                sender = sender,
                receiver = receiver,
                createdAt = null,
                isReceiverRead = false
            )
        }
    }

}