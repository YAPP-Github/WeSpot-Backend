package com.wespot.common.util

import com.wespot.ReasonPhraseUtil
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.net.URI

class ReasonPhraseUtilTest {

    @Test
    fun `HttpStatus내의 ReasonPhrase를 ProblemDetail내의 형식으로 변경한다`() {
        // given
        val prefixUrl = "/error"
        val httpStatus = HttpStatus.BAD_REQUEST
        val expected = URI.create("/error/bad-request")

        // when
        val actual = ReasonPhraseUtil.createErrorTypeInProblemDetail(prefixUrl, httpStatus)

        // then
        actual shouldBe expected
    }

}
