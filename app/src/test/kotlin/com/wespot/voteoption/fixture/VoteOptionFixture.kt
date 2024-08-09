package com.wespot.voteoption.fixture

import com.wespot.voteoption.VoteOption
import com.wespot.voteoption.VoteOptionContent
import java.time.LocalDateTime

object VoteOptionFixture {

    fun create() = VoteOption(
        id = 0L,
        content = VoteOptionContent.from("Mock 질문을 만듭니다."),
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
    )

    fun createWithId(id: Long) = VoteOption(
        id = id,
        content = VoteOptionContent.from("id가 지정된 질문지입니다."),
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
    )

}
