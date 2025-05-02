package com.wespot.voteoption.fixture

import com.wespot.common.BaseEntity
import com.wespot.common.util.RandomGenerator
import com.wespot.voteoption.VoteOption
import com.wespot.voteoption.VoteOptionContent
import com.wespot.voteoption.VoteOptionJpaEntity
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

    fun generate(
        id: Long = RandomGenerator.generateNonNullNumeric(5).toLong(),
        content: String = RandomGenerator.generateNonNullString(5),
        createdAt: LocalDateTime = LocalDateTime.now(),
        updatedAt: LocalDateTime = LocalDateTime.now(),
    ): VoteOption {
        return VoteOption(
            id = id,
            content = VoteOptionContent.from(content),
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }

    fun generateJpaEntity(
        id: Long = RandomGenerator.generateNonNullNumeric(5).toLong(),
        content: String = RandomGenerator.generateNonNullString(5),
        createdAt: LocalDateTime = LocalDateTime.now(),
        updatedAt: LocalDateTime = LocalDateTime.now(),
    ): VoteOptionJpaEntity {
        return VoteOptionJpaEntity(
            id = id,
            content = content,
            baseEntity = BaseEntity(
                createdAt = createdAt,
                updatedAt = updatedAt,
            ),
        )
    }

}
