package com.wespot.vote

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.User
import com.wespot.voteoption.VoteOption
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class CompleteBallot(
    val id: Long,
    val vote: Vote,
    val voteOption: VoteOption,
    val sender: User,
    val receiver: User,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    var isReceiverRead: Boolean,
) {

    companion object {
        fun of(
            vote: Vote,
            voteOption: VoteOption,
            sender: User,
            receiver: User,
            ballot: Ballot
        ): CompleteBallot {
            validate(ballot, vote, voteOption, sender, receiver)
            return CompleteBallot(
                id = ballot.id,
                vote = vote,
                voteOption = voteOption,
                sender = sender,
                receiver = receiver,
                createdAt = ballot.createdAt,
                updatedAt = ballot.updatedAt,
                isReceiverRead = ballot.isReceiverRead
            )
        }

        private fun validate(
            ballot: Ballot,
            vote: Vote,
            voteOption: VoteOption,
            sender: User,
            receiver: User
        ) {
            require(ballot.voteId == vote.id) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST,
                    ExceptionView.TOAST,
                    "입력된 투표가 잘못되었습니다."
                )
            }
            require(ballot.voteOptionId == voteOption.id) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST,
                    ExceptionView.TOAST,
                    "입력된 선택지가 잘못되었습니다."
                )
            }
            require(ballot.senderId == sender.id) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST,
                    ExceptionView.TOAST,
                    "입력된 송신자가 잘못되었습니다."
                )
            }
            require(ballot.receiverId == receiver.id) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST,
                    ExceptionView.TOAST,
                    "입력된 수신자가 잘못되었습니다."
                )
            }
        }
    }
}
