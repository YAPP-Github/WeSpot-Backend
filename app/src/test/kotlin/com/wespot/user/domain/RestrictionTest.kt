package com.wespot.user.domain

import com.wespot.user.Restriction
import com.wespot.user.RestrictionType
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import java.time.LocalDate

class RestrictionTest : BehaviorSpec({

    given("Restriction의") {
        `when`("초기 상태는") {
            val restriction = Restriction.createInitialState()
            then("None이고, 해제일은 LocalDate.MAX이다.") {
                restriction.restrictionType shouldBe RestrictionType.NONE
                restriction.releaseDate shouldBe LocalDate.MAX
            }
        }
    }

    given("Restriction에") {
        `when`("정상적인 값이 아닌 값이 입력되게 되면") {
            then("예외가 발생한다.") {
                val shouldThrow =
                    shouldThrow<IllegalArgumentException> { Restriction.of(RestrictionType.MESSAGE_USAGE, 29L) }
                val shouldThrow1 =
                    shouldThrow<IllegalArgumentException> { Restriction.of(RestrictionType.MESSAGE_USAGE, 31L) }
                val shouldThrow2 =
                    shouldThrow<IllegalArgumentException> { Restriction.of(RestrictionType.MESSAGE_USAGE, 89L) }
                val shouldThrow3 =
                    shouldThrow<IllegalArgumentException> { Restriction.of(RestrictionType.MESSAGE_USAGE, 91L) }
                val shouldThrow4 = shouldThrow<IllegalArgumentException> {
                    Restriction.of(
                        RestrictionType.MESSAGE_PERMANENT,
                        Long.MAX_VALUE - 1L
                    )
                }
                val shouldThrow5 = shouldThrow<IllegalArgumentException> {
                    Restriction.of(
                        RestrictionType.VOTE_PERMANENT,
                        Long.MAX_VALUE - 1L
                    )
                }

                shouldThrow1 shouldHaveMessage "올바르지 않은 제제 타입과 제제 일 수 입니다."
                shouldThrow2 shouldHaveMessage "올바르지 않은 제제 타입과 제제 일 수 입니다."
                shouldThrow3 shouldHaveMessage "올바르지 않은 제제 타입과 제제 일 수 입니다."
                shouldThrow4 shouldHaveMessage "올바르지 않은 제제 타입과 제제 일 수 입니다."
                shouldThrow5 shouldHaveMessage "올바르지 않은 제제 타입과 제제 일 수 입니다."
            }
        }
        `when`("정해진 규격의 값을 입력하게 되면") {
            val messageUsage1 = Restriction.of(RestrictionType.MESSAGE_USAGE, 30L)
            val messageUsage2 = Restriction.of(RestrictionType.MESSAGE_USAGE, 90L)
            val messagePermanent = Restriction.of(RestrictionType.MESSAGE_PERMANENT, Long.MAX_VALUE)
            val votePermanent = Restriction.of(RestrictionType.VOTE_PERMANENT, Long.MAX_VALUE)
            then("정상적으로 객체를 생성한다.") {
                messageUsage1.restrictionType shouldBe RestrictionType.MESSAGE_USAGE
                messageUsage1.releaseDate shouldBe LocalDate.now().plusDays(30)
                messageUsage2.restrictionType shouldBe RestrictionType.MESSAGE_USAGE
                messageUsage2.releaseDate shouldBe LocalDate.now().plusDays(90)
                messagePermanent.restrictionType shouldBe RestrictionType.MESSAGE_PERMANENT
                messagePermanent.releaseDate shouldBe LocalDate.MAX
                votePermanent.restrictionType shouldBe RestrictionType.VOTE_PERMANENT
                votePermanent.releaseDate shouldBe LocalDate.MAX
            }
        }
    }

})
