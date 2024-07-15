package com.wespot.vote

import com.wespot.common.BaseEntity
import java.time.LocalDateTime

object BallotJpaEntityFixture {

    fun createMock() = BallotJpaEntity(
        id = 1L,
        voteId = 1L,
        voteOptionId = 1L,
        senderId = 1L,
        receiverId = 1L,
        baseEntity = BaseEntity(LocalDateTime.now(), null),
        isReceiverRead = true
    )

}