package com.wespot.vote.domain

import com.wespot.exception.CustomException
import com.wespot.vote.Rate
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage

class RateTest : BehaviorSpec({

    given("Rate를 만들 때") {
        val validRate = 1
        `when`("0 이하의 등수가 입력되면") {
            then("예외가 발생한다.") {
                val shouldThrow = shouldThrow<CustomException> { Rate.from(0) }
                shouldThrow shouldHaveMessage "등수는 0 이하일 수 없습니다."
            }
        }
        `when`("정상적인 값이 입력되면") {
            val rate = Rate.from(validRate)
            then("정상적으로 생성된다.") {
                rate.value shouldBe validRate
            }
        }
    }

    given("의미없는") {
        `when`("Rate를 만들면") {
            val Rate = Rate.createMeaningLessRate()
            then("등수가 Int MAX VALUE로 설정되게 된다.") {
                Rate.value shouldBe Int.MAX_VALUE
            }
        }
    }

})
