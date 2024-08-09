package com.wespot.common.domain

import com.wespot.common.ProfanityChecker
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class BadWordsCheckerTest : BehaviorSpec({

    given("사용자가 내용을 입력할 때") {
        val badWords = listOf(
            "ㅅ_ㅂ",
            "ㅅ________________ㅂ",
            "너는 ㅅ!ㅂ",
            "너는 f@uck"
        )
        `when`("욕설이 존재하면") {
            println(ProfanityChecker.checkProfanity(badWords[0]))
            println(ProfanityChecker.checkProfanity(badWords[1]))
            println(ProfanityChecker.checkProfanity(badWords[2]))
            println(ProfanityChecker.checkProfanity(badWords[3]))
            val allMatch = badWords.stream()
                .allMatch { ProfanityChecker.checkProfanity(it) }
            then("true를 반환한다.") {
                allMatch shouldBe true
            }
        }
    }

})
