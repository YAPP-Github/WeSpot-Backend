package com.wespot.voteoption.domain

import com.wespot.voteoption.VoteOption
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDateTime

class VoteOptionTest() : BehaviorSpec() {

    init {
        given("정상적인 입력이 주어지고") {
            val id = null
            val content = "정상적인 입력"
            val createdAt = LocalDateTime.now()
            val updatedAt = null
            `when`("질문지를 생성하면") {
                val voteOption = VoteOption.of(id, content, createdAt, updatedAt)
                then("정상적으로 생성된다.") {
                    voteOption.id shouldBe id
                    voteOption.content shouldBe content
                    voteOption.createdAt shouldBe createdAt
                    voteOption.updatedAt shouldBe updatedAt
                }
            }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "     ", "               "])
    fun `정상적이지 않은 내용이 주어지고`(invalidContent: String) {
        // given
        val id = null
        val createdAt = LocalDateTime.now()
        val updatedAt = null

        // when
        val throwingCallable = { VoteOption.of(id, invalidContent, createdAt, updatedAt) }

        // then
        val shouldThrow = shouldThrow<IllegalArgumentException>(throwingCallable)
        shouldThrow.message shouldBe "선택지의 내용은 필수로 존재해야합니다."
    }

}
