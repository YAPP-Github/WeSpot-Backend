package com.wespot.vote.fixture

import com.wespot.vote.Ballot
import java.time.LocalDateTime

object BallotFixture {

    fun create() = Ballot(
        id = 1L,
        voteId = 1L,
        voteOptionId = 1L,
        senderId = 1L,
        receiverId = 1L,
        createdAt = LocalDateTime.now(),
        isReceiverRead = true
    )

    fun createByVoteAndVoteOptionAndSenderAndReceiver(
        voteId: Long,
        voteOptionId: Long,
        senderId: Long,
        receiverId: Long
    ) = Ballot(
        id = 0L,
        voteId = voteId,
        voteOptionId = voteOptionId,
        senderId = senderId,
        receiverId = receiverId,
        createdAt = LocalDateTime.now(),
        isReceiverRead = true
    )

}
