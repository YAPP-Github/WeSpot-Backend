package com.wespot.vote

import java.time.LocalDateTime

data class VoteMetrics(
    val userId: Long,
    val lastVotedDateTime: LocalDateTime,
    val voteCount: Int,
    val isReceiverRead: Boolean
) {

    companion object {

        fun createInitialState(userId: Long): VoteMetrics {
            return VoteMetrics(userId, LocalDateTime.MIN, 0, true)
        }

    }

    fun recordBallot(ballot: Ballot): VoteMetrics {
        return VoteMetrics(
            userId = userId,
            lastVotedDateTime = maxOf(lastVotedDateTime, ballot.createdAt),
            voteCount = voteCount + 1,
            isReceiverRead = isReceiverRead && ballot.isReceiverRead
        )
    }

}
