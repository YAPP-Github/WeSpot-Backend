package com.wespot.message.service

import com.wespot.message.MessageTimeValidator
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.throwable.shouldHaveMessage
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class MessageTimeValidatorTest : BehaviorSpec({

    beforeEach {
        MessageTimeValidator.resetClock()
    }

    given("MessageTimeValidator") {

        `when`("현재 시간이 17:00 이후이고 22:00 이전일 때") {
            then("validateMessageSendTime() 메서드는 예외를 던지지 않아야 한다") {
                // Arrange
                val fixedClock = Clock.fixed(Instant.parse("2023-03-18T18:00:00Z"), ZoneId.of("UTC"))
                MessageTimeValidator.setClock(fixedClock)

                // Act & Assert
                MessageTimeValidator.validateMessageSendTime()
            }
        }

        `when`("현재 시간이 22:00 이후일 때") {
            then("validateMessageSendTime() 메서드는 예외를 던져야 한다") {
                // Arrange
                val fixedClock = Clock.fixed(Instant.parse("2023-03-18T23:00:00Z"), ZoneId.of("UTC"))
                MessageTimeValidator.setClock(fixedClock)

                // Act & Assert
                val exception = shouldThrow<IllegalArgumentException> {
                    MessageTimeValidator.validateMessageSendTime()
                }
                exception shouldHaveMessage "이미 10시가 지나서 쪽지를 예약할 수 없어요\n아쉽지만 내일 다시 작성해보는 건 어떨까요?"
            }
        }

        `when`("현재 시간이 17:00 이전일 때") {
            then("validateMessageSendTime() 메서드는 예외를 던져야 한다") {
                // Arrange
                val fixedClock = Clock.fixed(Instant.parse("2023-03-18T16:00:00Z"), ZoneId.of("UTC"))
                MessageTimeValidator.setClock(fixedClock)

                // Act & Assert
                val exception = shouldThrow<IllegalArgumentException> {
                    MessageTimeValidator.validateMessageSendTime()
                }
                exception shouldHaveMessage "이미 10시가 지나서 쪽지를 예약할 수 없어요\n아쉽지만 내일 다시 작성해보는 건 어떨까요?"
            }
        }
    }
})
