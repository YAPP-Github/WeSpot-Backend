package com.wespot.user.domain

import com.wespot.user.RestrictionType
import com.wespot.user.restriction.VoteRestriction
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import java.time.LocalDate

class VoteRestrictionTest : BehaviorSpec({

    given("VoteRestriction의") {
        `when`("초기 상태는") {
            val voteRestriction = VoteRestriction.createInitialState()
            then("None이고, 해제일은 9999-12-31 이다.") {
                voteRestriction.restrictionType shouldBe RestrictionType.NONE
                voteRestriction.releaseDate shouldBe LocalDate.of(9999, 12, 31)
            }
        }
        `when`("제재 타입과 일 수를 정확하지 않게 입력하면") {
            val shouldThrow = shouldThrow<IllegalArgumentException> {
                VoteRestriction.of(
                    RestrictionType.PERMANENT_BAN_VOTE_REPORT,
                    Long.MAX_VALUE - 1
                )
            }
            then("예외가 발생한다.") {
                shouldThrow shouldHaveMessage "올바르지 않은 제재 타입과 제재 일 수 입니다."
            }
        }
        `when`("정확한 제재 타입과 일 수를 입력하면") {
            val restriction = VoteRestriction.of(
                RestrictionType.PERMANENT_BAN_VOTE_REPORT,
                Long.MAX_VALUE
            )
            then("정상적으로 생성된다.") {
                restriction.restrictionType shouldBe RestrictionType.PERMANENT_BAN_VOTE_REPORT
                restriction.releaseDate shouldBe LocalDate.of(9999, 12, 31)
            }
        }
        `when`("투표 타입이 아닌 제재를 입력하면") {
            val shouldThrow1 = shouldThrow<IllegalArgumentException> {
                VoteRestriction.of(
                    RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT,
                    30
                )
            }
            val shouldThrow2 = shouldThrow<IllegalArgumentException> {
                VoteRestriction.of(
                    RestrictionType.PERMANENT_BAN_MESSAGE_REPORT,
                    Long.MAX_VALUE
                )
            }
            then("예외가 발생한다.") {
                shouldThrow1 shouldHaveMessage "투표로 인한 제재 타입을 입력해주세요."
                shouldThrow2 shouldHaveMessage "투표로 인한 제재 타입을 입력해주세요."
            }
        }
        `when`("현재 시간을 기준으로 제재가 해제되었을 때") {
            val voteRestriction = VoteRestriction.createInitialState()
            val currentDate = LocalDate.now().plusDays(31)
            val currentRestriction = voteRestriction.getCurrentRestrictionBasedOnTime(currentDate)
            then("초기 상태로 변경된다.") {
                currentRestriction.restrictionType shouldBe RestrictionType.NONE
                currentRestriction.releaseDate shouldBe LocalDate.of(9999, 12, 31)
            }
        }
        `when`("제재가 해제되지 않았을 때, 아직 제재 중인 지를 확인하면") {
            val voteRestriction = VoteRestriction.of(
                RestrictionType.PERMANENT_BAN_VOTE_REPORT,
                Long.MAX_VALUE
            )
            val isKeepRestriction = voteRestriction.isKeepRestriction()
            then("true를 반환한다.") {
                isKeepRestriction shouldBe true
            }
        }
        `when`("제재가 해제되었을 때, 아직 제재 중인 지를 확인하면") {
            val voteRestriction = VoteRestriction.createInitialState()
            val isKeepRestriction = voteRestriction.isKeepRestriction()
            then("true를 반환한다.") {
                isKeepRestriction shouldBe false
            }
        }
    }


})
