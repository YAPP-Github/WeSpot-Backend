package com.wespot.user.domain

import com.wespot.exception.CustomException
import com.wespot.user.RestrictionType
import com.wespot.user.fixture.CommunityRestrictionFixture
import com.wespot.user.restriction.MessageRestriction
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import org.junit.Test
import java.time.LocalDate

class MessageRestrictionTest : BehaviorSpec({

    given("MessageRestriction의") {
        `when`("초기 상태는") {
            val messageRestriction = MessageRestriction.createInitialState()
            then("None이고, 해제일은 9999-12-31 이다.") {
                messageRestriction.restrictionType shouldBe RestrictionType.NONE
                messageRestriction.releaseDate shouldBe LocalDate.of(9999, 12, 31)
            }
        }
        `when`("제재 타입과 일 수를 정확하지 않게 입력하면") {
            val shouldThrow1 = shouldThrow<CustomException> {
                MessageRestriction.of(
                    RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT,
                    29L
                )
            }
            val shouldThrow2 = shouldThrow<CustomException> {
                MessageRestriction.of(
                    RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT,
                    31L
                )
            }
            val shouldThrow3 = shouldThrow<CustomException> {
                MessageRestriction.of(
                    RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT,
                    89L
                )
            }
            val shouldThrow4 = shouldThrow<CustomException> {
                MessageRestriction.of(
                    RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT,
                    91L
                )
            }
            val shouldThrow5 = shouldThrow<CustomException> {
                MessageRestriction.of(
                    RestrictionType.PERMANENT_BAN_MESSAGE_REPORT,
                    Long.MAX_VALUE - 1
                )
            }
            then("예외가 발생한다.") {
                shouldThrow1 shouldHaveMessage "올바르지 않은 제재 타입과 제재 일 수 입니다."
                shouldThrow2 shouldHaveMessage "올바르지 않은 제재 타입과 제재 일 수 입니다."
                shouldThrow3 shouldHaveMessage "올바르지 않은 제재 타입과 제재 일 수 입니다."
                shouldThrow4 shouldHaveMessage "올바르지 않은 제재 타입과 제재 일 수 입니다."
                shouldThrow5 shouldHaveMessage "올바르지 않은 제재 타입과 제재 일 수 입니다."
            }
        }
        `when`("정확한 제재 타입과 일 수를 입력하면") {
            val restriction1 = MessageRestriction.of(
                RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT,
                30L
            )
            val restriction2 = MessageRestriction.of(
                RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT,
                90L
            )
            val restriction3 = MessageRestriction.of(
                RestrictionType.PERMANENT_BAN_MESSAGE_REPORT,
                Long.MAX_VALUE
            )
            then("정상적으로 생성된다.") {
                restriction1.restrictionType shouldBe RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT
                restriction1.releaseDate shouldBe LocalDate.now().plusDays(30)
                restriction2.restrictionType shouldBe RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT
                restriction2.releaseDate shouldBe LocalDate.now().plusDays(90)
                restriction3.restrictionType shouldBe RestrictionType.PERMANENT_BAN_MESSAGE_REPORT
                restriction3.releaseDate shouldBe LocalDate.of(9999, 12, 31)
            }
        }
        `when`("쪽지 타입이 아닌 제재를 입력하면") {
            val shouldThrow = shouldThrow<CustomException> {
                MessageRestriction.of(
                    RestrictionType.PERMANENT_BAN_VOTE_REPORT,
                    Long.MAX_VALUE
                )
            }
            then("예외가 발생한다.") {
                shouldThrow shouldHaveMessage "쪽지로 인한 제재 타입을 입력해주세요."
            }
        }
        `when`("현재 시간을 기준으로 제재가 해제되었을 때") {
            val messageRestriction = MessageRestriction.of(
                RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT,
                30L
            )
            val currentDate = LocalDate.now().plusDays(31)
            val currentRestriction = messageRestriction.getCurrentRestrictionBasedOnTime(currentDate)
            then("초기 상태로 변경된다.") {
                currentRestriction.restrictionType shouldBe RestrictionType.NONE
                currentRestriction.releaseDate shouldBe LocalDate.of(9999, 12, 31)
            }
        }
        `when`("현재 시간을 기준으로 제재가 해제되지 않았을 때") {
            val messageRestriction = MessageRestriction.of(
                RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT,
                30L
            )
            val currentDate = LocalDate.now().plusDays(29)
            val currentRestriction = messageRestriction.getCurrentRestrictionBasedOnTime(currentDate)
            then("현재 제재 상태를 유지한다.") {
                currentRestriction.restrictionType shouldBe RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT
                currentRestriction.releaseDate shouldBe LocalDate.now().plusDays(30)
            }
        }
        `when`("제재가 해제되었을 때, 아직 제재 중인 지를 확인하면") {
            val messageRestriction = MessageRestriction.of(
                RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT,
                30L
            )
            val currentDate = LocalDate.now().plusDays(31)
            val isKeepRestriction = messageRestriction.getCurrentRestrictionBasedOnTime(currentDate)
                .isKeepRestriction()
            then("false를 반환한다.") {
                isKeepRestriction shouldBe false
            }
        }
        `when`("제재가 해제되지 않았을 때, 아직 제재 중인 지를 확인하면") {
            val messageRestriction = MessageRestriction.of(
                RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT,
                30L
            )
            val currentDate = LocalDate.now().plusDays(29)
            val isKeepRestriction = messageRestriction.getCurrentRestrictionBasedOnTime(currentDate)
                .isKeepRestriction()
            then("true를 반환한다.") {
                isKeepRestriction shouldBe true
            }
        }
    }

})
