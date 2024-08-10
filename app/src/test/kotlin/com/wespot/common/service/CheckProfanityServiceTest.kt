package com.wespot.common.service

import com.wespot.common.dto.CheckProfanityRequest
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.throwable.shouldHaveMessage
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class CheckProfanityServiceTest @Autowired constructor(
    private val checkProfanityService: CheckProfanityService
) : ServiceTest() {

    @Test
    fun `욕설이 아닌 값이 입력되면 예외를 반환하지 않는다`() {
        // given
        val validWord = "안녕"
        val checkProfanityRequest = CheckProfanityRequest(validWord)

        // when then
        shouldNotThrow<IllegalArgumentException> { checkProfanityService.checkProfanity(checkProfanityRequest) }
    }

    @Test
    fun `욕설이 입력되면 예외를 반환한다`() {
        // given
        val badWord = "너는 ㅅ_____________112231ㅂ"
        val checkProfanityRequest = CheckProfanityRequest(badWord)

        // when
        val shouldThrow =
            shouldThrow<IllegalArgumentException> { checkProfanityService.checkProfanity(checkProfanityRequest) }

        // then
        shouldThrow shouldHaveMessage "비속어가 포함되어 있습니다."
    }

}
