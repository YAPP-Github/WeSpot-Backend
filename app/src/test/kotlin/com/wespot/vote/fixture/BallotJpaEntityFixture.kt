package com.wespot.vote.fixture

import com.wespot.common.BaseEntity
import com.wespot.vote.BallotJpaEntity
import java.time.LocalDateTime

object BallotJpaEntityFixture {

    fun create() = BallotJpaEntity(
        id = 1L,
        voteId = 1L,
        voteOptionId = 1L,
        senderId = 1L,
        receiverId = 1L,
        baseEntity = BaseEntity(LocalDateTime.now(), LocalDateTime.now()),
        isReceiverRead = true
    )

}
