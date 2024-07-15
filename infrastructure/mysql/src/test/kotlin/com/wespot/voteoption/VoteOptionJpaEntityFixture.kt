package com.wespot.voteoption

import com.wespot.common.BaseEntity
import java.time.LocalDateTime

object VoteOptionJpaEntityFixture {

    fun createMock() = VoteOptionJpaEntity(
        id = 1L,
        content = "Mock 질문을 만듭니다.",
        baseEntity = BaseEntity(LocalDateTime.now(), null),
    )

}