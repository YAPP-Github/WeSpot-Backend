package com.wespot.common.domain

import com.wespot.common.ProfanityChecker
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage

class ProfanityCheckerTest : BehaviorSpec({

    given("사용자가 내용을 입력할 때") {
        val badWords = listOf(
            "ㅅ_ㅂ",
            "ㅅ________________ㅂ",
            "너는 ㅅ!ㅂ",
            "너는 f@uck"
        )
        `when`("욕설이 존재하면") {
            val allMatch = badWords.stream()
                .allMatch { ProfanityChecker.checkProfanity(it) }
            then("true를 반환한다.") {
                allMatch shouldBe true
            }
        }
        `when`("욕설이 존재하면") {
            val shouldThrow1 = shouldThrow<IllegalArgumentException> { ProfanityChecker.validateContent(badWords[0]) }
            val shouldThrow2 = shouldThrow<IllegalArgumentException> { ProfanityChecker.validateContent(badWords[1]) }
            val shouldThrow3 = shouldThrow<IllegalArgumentException> { ProfanityChecker.validateContent(badWords[2]) }
            val shouldThrow4 = shouldThrow<IllegalArgumentException> { ProfanityChecker.validateContent(badWords[3]) }
            then("에외를 발생시킨다.") {
                shouldThrow1 shouldHaveMessage "비속어가 포함되어 있습니다."
                shouldThrow2 shouldHaveMessage "비속어가 포함되어 있습니다."
                shouldThrow3 shouldHaveMessage "비속어가 포함되어 있습니다."
                shouldThrow4 shouldHaveMessage "비속어가 포함되어 있습니다."
            }
        }
    }

})
