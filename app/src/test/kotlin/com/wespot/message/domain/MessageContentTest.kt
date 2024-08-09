package com.wespot.message.domain

import com.wespot.message.MessageContent
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage

class MessageContentTest : BehaviorSpec({

    given("메시지 컨텐츠에") {
        val badWordsContent = "ㅂㅁㄴ이;라ㅓ 싮ㅂㅅㅂㅅㅂㅅㅂ시ㅂ 메시지"
        val emptyContent = ""
        val validContent = "헬로우"
        `when`("욕설이 포함되어 있는 경우") {
            val shouldThrow = shouldThrow<IllegalArgumentException> { MessageContent.from(badWordsContent) }
            then("예외가 발생한다.") {
                shouldThrow shouldHaveMessage "메시지의 내용에는 욕설이 포함될 수 없습니다."
            }
        }
        `when`("아무런 내용이 없는 경우") {
            val shouldThrow = shouldThrow<IllegalArgumentException> { MessageContent.from(emptyContent) }
            then("예외가 발생한다.") {
                shouldThrow shouldHaveMessage "메시지의 내용은 필수로 존재해야합니다."
            }
        }
        `when`("정상적인 값이 입력되는 경우") {
            val messageContent = MessageContent.from(validContent)
            then("예외가 발생하지 않는다.") {
                messageContent.content shouldBe validContent
            }
        }
    }

})
