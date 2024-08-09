package com.wespot.user.domain

import com.wespot.user.UserIntroduction
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage

class UserIntroductionTest : BehaviorSpec({

    given("소개에") {
        val badWordsIntroduction = "ㅅㅂㅅㅂㅅㅂㅅㅂㅅㅂㅅㅂㅂㅂㅂㅂㅂ"
        val validIntroduction = "헬로우"
        `when`("욕설이 포함되어 있는 경우") {
            val shouldThrow = shouldThrow<IllegalArgumentException> { UserIntroduction.from(badWordsIntroduction) }
            then("예외가 발생한다.") {
                shouldThrow shouldHaveMessage "소개에는 욕설이 포함될 수 없습니다."
            }
        }
        `when`("정상적인 값이 입력되는 경우") {
            val userIntroduction = UserIntroduction.from(validIntroduction)
            then("예외가 발생하지 않는다.") {
                userIntroduction.introduction shouldBe validIntroduction
            }
        }
    }

    given("처음 소개를 생성할 때") {
        val expectedIntroduction = ""
        `when`("빈 문자열로") {
            val actual = UserIntroduction.emptyUserIntroduction()
            then("생성한다.") {
                actual.introduction shouldBe expectedIntroduction
            }
        }
    }

})
