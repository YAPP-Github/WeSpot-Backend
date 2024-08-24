package com.wespot.user.domain

import com.wespot.exception.CustomException
import com.wespot.user.RestrictionType
import com.wespot.user.restriction.Restriction
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import java.time.LocalDate

class RestrictionTest : BehaviorSpec({

    given("Restriction의") {
        `when`("초기 상태는") {
            val restriction = Restriction.createInitialState()
            then("None이고, 해제일은 9999-12-31 이다.") {
                restriction.voteRestriction.restrictionType shouldBe RestrictionType.NONE
                restriction.messageRestriction.restrictionType shouldBe RestrictionType.NONE
                restriction.voteRestriction.releaseDate shouldBe LocalDate.of(9999, 12, 31)
                restriction.messageRestriction.releaseDate shouldBe LocalDate.of(9999, 12, 31)
            }
        }
    }

    given("Restriction을") {
        `when`("정상적인 값이 아닌 값으로 변경하게 되면") {
            then("예외가 발생한다.") {
                val initialRestriction = Restriction.createInitialState()
                val shouldThrow =
                    shouldThrow<CustomException> {
                        initialRestriction.addRestrict(
                            RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT,
                            29L
                        )
                    }
                val shouldThrow1 =
                    shouldThrow<CustomException> {
                        initialRestriction.addRestrict(
                            RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT,
                            31L
                        )
                    }
                val shouldThrow2 =
                    shouldThrow<CustomException> {
                        initialRestriction.addRestrict(
                            RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT,
                            89L
                        )
                    }
                val shouldThrow3 =
                    shouldThrow<CustomException> {
                        initialRestriction.addRestrict(
                            RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT,
                            91L
                        )
                    }
                val shouldThrow4 = shouldThrow<CustomException> {
                    initialRestriction.addRestrict(
                        RestrictionType.PERMANENT_BAN_MESSAGE_REPORT,
                        Long.MAX_VALUE - 1L
                    )
                }
                val shouldThrow5 = shouldThrow<CustomException> {
                    initialRestriction.addRestrict(
                        RestrictionType.PERMANENT_BAN_VOTE_REPORT,
                        Long.MAX_VALUE - 1L
                    )
                }

                shouldThrow shouldHaveMessage "올바르지 않은 제재 타입과 제재 일 수 입니다."
                shouldThrow1 shouldHaveMessage "올바르지 않은 제재 타입과 제재 일 수 입니다."
                shouldThrow2 shouldHaveMessage "올바르지 않은 제재 타입과 제재 일 수 입니다."
                shouldThrow3 shouldHaveMessage "올바르지 않은 제재 타입과 제재 일 수 입니다."
                shouldThrow4 shouldHaveMessage "올바르지 않은 제재 타입과 제재 일 수 입니다."
                shouldThrow5 shouldHaveMessage "올바르지 않은 제재 타입과 제재 일 수 입니다."
            }
        }
        `when`("정해진 규격의 값으로 변경하면") {
            val initialRestriction = Restriction.createInitialState()
            val messageUsage1 = initialRestriction.addRestrict(RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT, 30L)
            val messageUsage2 = initialRestriction.addRestrict(RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT, 90L)
            val messagePermanent =
                initialRestriction.addRestrict(RestrictionType.PERMANENT_BAN_MESSAGE_REPORT, Long.MAX_VALUE)
            val votePermanent =
                initialRestriction.addRestrict(RestrictionType.PERMANENT_BAN_VOTE_REPORT, Long.MAX_VALUE)
            then("정상적으로 객체를 생성한다.") {
                messageUsage1.messageRestriction.restrictionType shouldBe RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT
                messageUsage1.messageRestriction.releaseDate shouldBe LocalDate.now().plusDays(30)
                messageUsage2.messageRestriction.restrictionType shouldBe RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT
                messageUsage2.messageRestriction.releaseDate shouldBe LocalDate.now().plusDays(90)
                messagePermanent.messageRestriction.restrictionType shouldBe RestrictionType.PERMANENT_BAN_MESSAGE_REPORT
                messagePermanent.messageRestriction.releaseDate shouldBe LocalDate.of(9999, 12, 31)
                votePermanent.voteRestriction.restrictionType shouldBe RestrictionType.PERMANENT_BAN_VOTE_REPORT
                votePermanent.voteRestriction.releaseDate shouldBe LocalDate.of(9999, 12, 31)
            }
        }
    }

    given("제재를 추가할 때") {
        val initialRestriction = Restriction.createInitialState()
        `when`("None을 추가하면") {
            val shouldThrow =
                shouldThrow<CustomException> { initialRestriction.addRestrict(RestrictionType.NONE, 0) }
            then("예외가 발생한다.") {
                shouldThrow shouldHaveMessage "RestrictionType.NONE을 추가할 수 없습니다."
            }
        }
        `when`("정상적인 값을 추가하면") {
            val newRestriction = initialRestriction.addRestrict(RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT, 30L)
            then("정상적으로 추가된다.") {
                newRestriction.messageRestriction.restrictionType shouldBe RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT
                newRestriction.messageRestriction.releaseDate shouldBe LocalDate.now().plusDays(30)
            }
        }
    }

    given("여러 제재가 존재하더라도") {
        val initialRestriction = Restriction.createInitialState()
        `when`("주어진 시간에 따라 제재가") {
            val messageRestriction = initialRestriction.addRestrict(RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT, 90)
            val restriction =
                messageRestriction.addRestrict(RestrictionType.PERMANENT_BAN_VOTE_REPORT, Long.MAX_VALUE)
            then("정상적으로 생긴다.") {
                restriction.voteRestriction.restrictionType shouldBe RestrictionType.PERMANENT_BAN_VOTE_REPORT
                restriction.voteRestriction.releaseDate shouldBe LocalDate.of(9999, 12, 31)
                restriction.messageRestriction.restrictionType shouldBe RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT
                restriction.messageRestriction.releaseDate shouldBe LocalDate.now().plusDays(90)
            }
        }
    }

    given("하나의") {
        val initialRestriction = Restriction.createInitialState()
        `when`("제재라도 걸려있으면") {
            val messageRestriction = initialRestriction.addRestrict(RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT, 90)
            val voteRestriction =
                messageRestriction.addRestrict(RestrictionType.PERMANENT_BAN_VOTE_REPORT, Long.MAX_VALUE)
            val restriction =
                voteRestriction.getCurrentRestrictionBasedOnTime(LocalDate.now().plusDays(91))
            then("제재중인 것으로 판단된다.") {
                restriction.messageRestriction.restrictionType shouldBe RestrictionType.NONE
                restriction.voteRestriction.restrictionType shouldBe RestrictionType.PERMANENT_BAN_VOTE_REPORT
                restriction.isKeepRestriction()
            }
        }
    }

})
