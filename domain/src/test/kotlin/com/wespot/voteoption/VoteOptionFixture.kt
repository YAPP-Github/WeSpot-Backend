package com.wespot.voteoption

import java.time.LocalDateTime

object VoteOptionFixture {

    fun createMock() = VoteOption(
        id = 1L,
        content = "Mock 질문을 만듭니다.",
        createdAt = LocalDateTime.now(),
        updatedAt = null,
    )

}