package com.wespot.message.domain

import com.wespot.message.fixture.MessageFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import io.mockk.every
import io.mockk.mockkStatic
import java.time.LocalDateTime

class MessageTest : BehaviorSpec({

    given("메시지를") {
        val message = MessageFixture.createWithIdAndSenderIdAndReceiverId(1, 1, 2)

        `when`("수신한자가 메시지를 신고한 경우") {
            val now = LocalDateTime.now()
            mockkStatic(LocalDateTime::class)
            every { LocalDateTime.now() } returns now
            val reportedMessage = message.reported(2)

            then("메시지 신고 처리가 된다.") {
                reportedMessage.isReceiverDeleted shouldBe true
                reportedMessage.isReported shouldBe true
                reportedMessage.receiverDeletedAt shouldBe now
                reportedMessage.updatedAt shouldBe now
            }
        }
        `when`("수신하지 않은 자가 메시지를 신고한 경우") {
            val shouldThrow = shouldThrow<IllegalArgumentException> { message.reported(3) }

            then("예외가 발생한다.") {
                shouldThrow shouldHaveMessage "수신자만이 메시지를 신고할 수 있습니다."
            }
        }
    }

})
