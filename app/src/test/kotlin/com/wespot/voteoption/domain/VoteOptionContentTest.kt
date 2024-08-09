package com.wespot.voteoption.domain

import com.wespot.voteoption.VoteOptionContent
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.throwable.shouldHaveMessage

class VoteOptionContentTest : BehaviorSpec({

    given("선택지에") {
        val badWordsContent = "ㅂㅁㄴ이;라ㅓ 싮ㅂㅅㅂㅅㅂㅅㅂ시ㅂ 선택지"
        `when`("욕설이 포함되어 있는 경우") {
            val shouldThrow = shouldThrow<IllegalArgumentException> { VoteOptionContent.from(badWordsContent) }
            then("예외가 발생한다.") {
                shouldThrow shouldHaveMessage "선택지의 내용에는 욕설이 포함될 수 없습니다."
            }
        }
    }

})
