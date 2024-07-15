package com.wespot.voteoption.fixture

import com.wespot.voteoption.VoteOption
import java.time.LocalDateTime

object VoteOptionFixture {

    fun create() = VoteOption(
        id = 1L,
        content = "Mock 질문을 만듭니다.",
        createdAt = LocalDateTime.now(),
        updatedAt = null,
    )

    fun createWithId(id: Long?) = VoteOption(
        id = id,
        content = "id가 지정된 질문지입니다.",
        createdAt = LocalDateTime.now(),
        updatedAt = null,
    )

}