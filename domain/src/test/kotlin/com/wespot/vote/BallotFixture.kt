package com.wespot.vote

import java.time.LocalDateTime

object BallotFixture {

    fun createMock() = Ballot(
        id = 1L,
        voteId = 1L,
        voteOptionId = 1L,
        senderId = 1L,
        receiverId = 1L,
        createdAt = LocalDateTime.now(),
        isReceiverRead = true
    )

}