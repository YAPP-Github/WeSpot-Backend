package com.wespot.vote

import java.time.LocalDateTime
import java.util.*

data class Ballot(
    val id: Long?,
    val voteId: Long,
    val voteOptionId: Long,
    val senderId: Long,
    val receiverId: Long,
    val createdAt: LocalDateTime,
    val isReceiverRead: Boolean,
) {

    companion object {
        fun of(voteId: Long, voteOptionId: Long, senderId: Long, receiverId: Long): Ballot {
            validateVote(voteId)
            validateVoteOption(voteOptionId)
            validateSenderAndReceiver(senderId, receiverId)
            return Ballot(
                id = null,
                voteId = voteId,
                voteOptionId = voteOptionId,
                senderId = senderId,
                receiverId = receiverId,
                createdAt = LocalDateTime.now(),
                isReceiverRead = false
            )
        }

        private fun validateVote(voteId: Long) {
            if (Objects.isNull(voteId)) {
                throw IllegalArgumentException("존재하지 않는 투표함에 투표할 수 없습니다.")
            }
        }

        private fun validateVoteOption(voteOptionId: Long) {
            if (Objects.isNull(voteOptionId)) {
                throw IllegalArgumentException("질문지 선택은 필수 입니다.")
            }
        }

        private fun validateSenderAndReceiver(senderId: Long, receiverId: Long) {
            if (senderId == receiverId) {
                throw IllegalArgumentException("본인을 투표할 수 없습니다.")
            }
        }

    }

}