package com.wespot.message.domain

import com.wespot.exception.CustomException
import com.wespot.message.Message
import com.wespot.message.MessageTimeValidator
import com.wespot.message.fixture.MessageFixture
import com.wespot.user.RestrictionType
import com.wespot.user.fixture.ProfileFixture
import com.wespot.user.fixture.UserFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class MessageTest : BehaviorSpec({

    beforeContainer {
        val fixedClock = Clock.fixed(Instant.parse("2023-03-18T18:00:00Z"), ZoneId.of("UTC"))
        MessageTimeValidator.setClock(fixedClock)
    }

    afterContainer {
        clearAllMocks()
        MessageTimeValidator.resetClock()
    }

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
            val shouldThrow = shouldThrow<CustomException> { message.reported(3) }

            then("예외가 발생한다.") {
                shouldThrow shouldHaveMessage "수신자만이 메시지를 신고할 수 있습니다."
            }
        }
    }

    given("메시지 컨텐츠에") {
        mockkStatic(LocalDateTime::class)
        every { LocalDateTime.now() } returns LocalDateTime.of(2024, 8, 9, 19, 0)
        val badWordsContent = "ㅂㅁㄴ이;라ㅓ 싮ㅂㅅㅂㅅㅂㅅㅂ시ㅂ 메시지"
        val emptyContent = ""
        `when`("욕설이 포함되어 있는 경우") {
            val shouldThrow = shouldThrow<CustomException> {
                Message.sendMessage(
                    badWordsContent,
                    UserFixture.createWithId(1),
                    UserFixture.createWithId(2),
                    "senderName",
                    false
                )
            }
            then("예외가 발생한다.") {
                shouldThrow shouldHaveMessage "메시지의 내용에 비속어가 포함되어 있습니다."
            }
        }
        `when`("아무런 내용이 없는 경우") {
            val shouldThrow = shouldThrow<CustomException> {
                Message.sendMessage(
                    emptyContent,
                    UserFixture.createWithId(1),
                    UserFixture.createWithId(2),
                    "senderName",
                    false
                )
            }
            then("예외가 발생한다.") {
                shouldThrow shouldHaveMessage "메시지의 내용은 필수로 존재해야합니다."
            }
        }
        unmockkStatic(LocalDateTime::class)
    }

    given("쪽지를 수정할 때") {
        val restrictionUser =
            UserFixture.createUserWithIdAndRestrictionTypeAndRestrictDay(
                1,
                listOf(Pair(RestrictionType.PERMANENT_BAN_MESSAGE_REPORT, Long.MAX_VALUE))
            )
        val withDrawUser = UserFixture.createWithId(2).withdraw().completeWithdraw(ProfileFixture.createWithId(1))
        val restrictionUserMessage = MessageFixture.createMessage("Hello", 2, 1, "senderName")
        val withDrawUserMessage = MessageFixture.createMessage("Hello", 1, 2, "senderName")
        `when`("수정하는 자가 탈퇴 혹은 이용제재를 당한 사용자라면") {
            val shouldThrow1 = shouldThrow<CustomException> {
                restrictionUserMessage.updateMessage(
                    "Hello1",
                    restrictionUser,
                    withDrawUser.id,
                    restrictionUser.name
                )
            }
            val shouldThrow2 = shouldThrow<CustomException> {
                withDrawUserMessage.updateMessage(
                    "Hello2",
                    withDrawUser,
                    restrictionUser.id,
                    withDrawUser.name
                )
            }
            then("예외가 발생한다.") {
                shouldThrow1 shouldHaveMessage "이용제한을 당한 학생은 해당 서비스를 이용할 수 없습니다."
                shouldThrow2 shouldHaveMessage "탈퇴한 학생은 해당 서비스를 이용할 수 없습니다."
            }
        }
    }

    given("쪽지를 보낼 때") {
        val restrictionUser =
            UserFixture.createUserWithIdAndRestrictionTypeAndRestrictDay(
                1,
                listOf(Pair(RestrictionType.PERMANENT_BAN_MESSAGE_REPORT, Long.MAX_VALUE))
            )
        val withDrawUser = UserFixture.createWithId(2).withdraw().completeWithdraw(ProfileFixture.createWithId(1))
        `when`("송신자가 탈퇴 혹은 이용제재를 당한 사용자라면") {
            val shouldThrow1 = shouldThrow<CustomException> {
                Message.sendMessage(
                    "hello",
                    UserFixture.createWithId(3),
                    withDrawUser,
                    withDrawUser.name,
                    false,
                )
            }
            val shouldThrow2 = shouldThrow<CustomException> {
                Message.sendMessage(
                    "hello",
                    UserFixture.createWithId(3),
                    restrictionUser,
                    restrictionUser.name,
                    false
                )
            }

            then("예외가 발생한다.") {
                shouldThrow1 shouldHaveMessage "탈퇴한 학생은 해당 서비스를 이용할 수 없습니다."
                shouldThrow2 shouldHaveMessage "이용제한을 당한 학생은 해당 서비스를 이용할 수 없습니다."
            }
        }

        `when`("수신자가 탈퇴 혹은 이용제재를 당한 사용자라면") {
            val shouldThrow1 = shouldThrow<CustomException> {
                Message.sendMessage(
                    "hello",
                    withDrawUser,
                    UserFixture.createWithId(3),
                    "senderName",
                    false,
                )
            }
            val shouldThrow2 = shouldThrow<CustomException> {
                Message.sendMessage(
                    "hello",
                    restrictionUser,
                    UserFixture.createWithId(3),
                    "senderName",
                    false
                )
            }

            then("예외가 발생한다.") {
                shouldThrow1 shouldHaveMessage "탈퇴한 학생은 해당 서비스를 이용할 수 없습니다."
                shouldThrow2 shouldHaveMessage "이용제한을 당한 학생은 해당 서비스를 이용할 수 없습니다."
            }
        }
    }

    given("받은 쪽지를 삭제할 때") {
        val restrictionUser =
            UserFixture.createUserWithIdAndRestrictionTypeAndRestrictDay(
                1,
                listOf(Pair(RestrictionType.PERMANENT_BAN_MESSAGE_REPORT, Long.MAX_VALUE))
            )
        val withDrawUser = UserFixture.createWithId(2).withdraw().completeWithdraw(ProfileFixture.createWithId(1))
        val restrictionUserMessage = MessageFixture.createMessage("Hello", 2, 1, "senderName")
        val withDrawUserMessage = MessageFixture.createMessage("Hello", 1, 2, "senderName")
        `when`("수신자가 탈퇴 혹은 이용제재를 당한 사용자라면") {
            val shouldThrow1 = shouldThrow<CustomException> {
                withDrawUserMessage.receivedMessageSoftDelete(restrictionUser)
            }
            val shouldThrow2 = shouldThrow<CustomException> {
                restrictionUserMessage.receivedMessageSoftDelete(withDrawUser)
            }

            then("예외가 발생한다.") {
                shouldThrow1 shouldHaveMessage "이용제한을 당한 학생은 해당 서비스를 이용할 수 없습니다."
                shouldThrow2 shouldHaveMessage "탈퇴한 학생은 해당 서비스를 이용할 수 없습니다."
            }
        }
    }

    given("보낸 쪽지를 삭제할 때") {
        val restrictionUser =
            UserFixture.createUserWithIdAndRestrictionTypeAndRestrictDay(
                1,
                listOf(Pair(RestrictionType.PERMANENT_BAN_MESSAGE_REPORT, Long.MAX_VALUE))
            )
        val withDrawUser = UserFixture.createWithId(2).withdraw().completeWithdraw(ProfileFixture.createWithId(1))
        val restrictionUserMessage = MessageFixture.createMessage("Hello", 2, 1, "senderName")
        val withDrawUserMessage = MessageFixture.createMessage("Hello", 1, 2, "senderName")
        `when`("수신자가 탈퇴 혹은 이용제재를 당한 사용자라면") {
            val shouldThrow1 = shouldThrow<CustomException> {
                restrictionUserMessage.sendMessageSoftDelete(withDrawUser)
            }
            val shouldThrow2 = shouldThrow<CustomException> {
                withDrawUserMessage.sendMessageSoftDelete(restrictionUser)
            }

            then("예외가 발생한다.") {
                shouldThrow1 shouldHaveMessage "탈퇴한 학생은 해당 서비스를 이용할 수 없습니다."
                shouldThrow2 shouldHaveMessage "이용제한을 당한 학생은 해당 서비스를 이용할 수 없습니다."
            }
        }
    }

})
