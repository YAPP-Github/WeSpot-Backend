package com.wespot.vote

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import org.springframework.http.HttpStatus
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class Ballot(
    val id: Long,
    val voteId: Long,
    val voteOptionId: Long,
    val senderId: Long,
    val receiverId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    var isReceiverRead: Boolean,
) {

    companion object {
        fun of(
            voteId: Long,
            voteDate: LocalDate,
            voteOptionId: Long,
            senderId: Long,
            receiverId: Long,
            voteTime: LocalDateTime
        ): Ballot {
            validateVote(voteId)
            validateVoteOption(voteOptionId)
            validateSenderAndReceiver(senderId, receiverId)
            validateVoteDateTime(voteDate, voteTime)
            return Ballot(
                id = 0L,
                voteId = voteId,
                voteOptionId = voteOptionId,
                senderId = senderId,
                receiverId = receiverId,
                createdAt = voteTime,
                updatedAt = voteTime,
                isReceiverRead = false
            )
        }

        private fun validateVote(voteId: Long) {
            if (Objects.isNull(voteId)) {
                throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "존재하지 않는 투표함에 투표할 수 없습니다.")
            }
        }

        private fun validateVoteOption(voteOptionId: Long) {
            if (Objects.isNull(voteOptionId)) {
                throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "질문지 선택은 필수 입니다.")
            }
        }

        private fun validateSenderAndReceiver(senderId: Long, receiverId: Long) {
            if (senderId == receiverId) {
                throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "본인을 투표할 수 없습니다.")
            }
        }

        private fun validateVoteDateTime(voteDate: LocalDate, voteTime: LocalDateTime) {
            require(voteDate == voteTime.toLocalDate()) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST,
                    ExceptionView.TOAST,
                    "투표한 시각이 잘못되었습니다."
                )
            }
        }

    }

    fun receiverRead() {
        isReceiverRead = true
    }

    override fun toString(): String {
        return "Ballot(id=$id, voteId=$voteId, voteOptionId=$voteOptionId, senderId=$senderId, receiverId=$receiverId, createdAt=$createdAt, updatedAt=$updatedAt, isReceiverRead=$isReceiverRead)"
    }

}
