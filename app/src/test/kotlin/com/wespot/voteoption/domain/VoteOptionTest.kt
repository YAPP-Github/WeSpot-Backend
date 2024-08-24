package com.wespot.voteoption.domain

import com.wespot.exception.CustomException
import com.wespot.voteoption.VoteOption
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDateTime

class VoteOptionTest() : BehaviorSpec() {

    init {
        given("정상적인 입력이 주어지고") {
            val id = 0L
            val content = "정상적인 입력"
            val createdAt = LocalDateTime.now()
            val updatedAt = LocalDateTime.now()
            `when`("질문지를 생성하면") {
                val voteOption = VoteOption.of(id, content, createdAt, updatedAt)
                then("정상적으로 생성된다.") {
                    voteOption.id shouldBe id
                    voteOption.content.content shouldBe content
                    voteOption.createdAt shouldBe createdAt
                    voteOption.updatedAt shouldBe updatedAt
                }
            }
        }

        given("선택지에") {
            val badWordsContent = "ㅅㅂㅂㅂㅂㅂㅂㅂㅂㅂ 선택지"
            `when`("욕설이 포함되어 있는 경우") {
                val shouldThrow = shouldThrow<CustomException> {
                    VoteOption.of(
                        0L,
                        badWordsContent,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                    )
                }
                then("예외가 발생한다.") {
                    shouldThrow shouldHaveMessage "선택지의 내용에 비속어가 포함되어 있습니다."
                }
            }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "     ", "               "])
    fun `정상적이지 않은 내용이 주어지고`(invalidContent: String) {
        // given
        val id = 0L
        val createdAt = LocalDateTime.now()
        val updatedAt = LocalDateTime.now()

        // when
        val throwingCallable = { VoteOption.of(id, invalidContent, createdAt, updatedAt) }

        // then
        val shouldThrow = shouldThrow<CustomException>(throwingCallable)
        shouldThrow.message shouldBe "선택지의 내용은 필수로 존재해야합니다."
    }

}
