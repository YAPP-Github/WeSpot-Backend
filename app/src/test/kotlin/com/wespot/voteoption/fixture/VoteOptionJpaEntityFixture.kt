package com.wespot.voteoption.fixture

import com.wespot.common.BaseEntity
import com.wespot.voteoption.VoteOptionJpaEntity
import java.time.LocalDateTime

object VoteOptionJpaEntityFixture {

    fun create() = VoteOptionJpaEntity(
        id = 1L,
        content = "Mock 질문을 만듭니다.",
        baseEntity = BaseEntity(LocalDateTime.now(), LocalDateTime.now()),
    )

}
