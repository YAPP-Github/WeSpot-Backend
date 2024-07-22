package com.wespot.vote

import com.wespot.user.User
import java.time.LocalDateTime
import java.util.*

data class VoteRecord(
    val user: User,
    val rate: Int,
    val lastVotedDateTime: LocalDateTime,
    val voteCount: Int,
    val isReceiverRead: Boolean
) {

    companion object {
        fun of(
            user: User,
            voteMetrics: VoteMetrics
        ): VoteRecord {
            validateInvalidUser(user)
            return VoteRecord(
                user,
                rate = Int.MAX_VALUE,
                voteMetrics.lastVotedDateTime,
                voteMetrics.voteCount,
                voteMetrics.isReceiverRead
            )
        }

        private fun validateInvalidUser(user: User) {
            if (Objects.isNull(user)) {
                throw IllegalArgumentException("존재하지 않는 사용자에 대한 투표가 존재합니다.")
            }
        }

        fun ofWithRate(
            user: User,
            rate: Int,
            voteMetrics: VoteMetrics
        ): VoteRecord {
            return VoteRecord(
                user,
                rate,
                voteMetrics.lastVotedDateTime,
                voteMetrics.voteCount,
                voteMetrics.isReceiverRead
            )
        }
    }

}
