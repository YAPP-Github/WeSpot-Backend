package com.wespot.auth.service

import com.wespot.auth.KakaoTemplate
import com.wespot.auth.KakaoTemplateType
import com.wespot.auth.dto.response.KakaoTemplateResponse
import com.wespot.auth.fixture.KakaoTemplateFixture.createKakaoTemplate
import com.wespot.auth.port.out.KakaoTemplatePort
import com.wespot.auth.service.kakao.KakaoTemplateService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.every
import io.mockk.mockk


class KakaoTemplateServiceTest : BehaviorSpec({

    val kakaoTemplatePort = mockk<KakaoTemplatePort>()
    val kakaoTemplateService = KakaoTemplateService(kakaoTemplatePort)

    given("KakaoTemplateService") {

        `when`("getKakaoTemplate 호출 시 템플릿이 존재하는 경우") {
            val type = KakaoTemplateType.TELL
            val kakaoTemplate = createKakaoTemplate()
            val expectedResponse = KakaoTemplateResponse.from(kakaoTemplate)

            every { kakaoTemplatePort.getKakaoTemplate(type) } returns kakaoTemplate

            val result = kakaoTemplateService.getKakaoTemplate(type)

            then("해당 템플릿을 정상적으로 반환해야 한다") {
                result shouldBe expectedResponse
            }
        }

        `when`("getKakaoTemplate 호출 시 템플릿이 존재하지 않는 경우") {
            val type = KakaoTemplateType.FIND

            every { kakaoTemplatePort.getKakaoTemplate(type) } returns null

            then("NoSuchElementException이 발생해야 한다") {
                shouldThrow<NoSuchElementException> {
                    kakaoTemplateService.getKakaoTemplate(type)
                }.message shouldBe "해당 타입에는 카카오 템플릿이 존재하지 않습니다."
            }
        }
    }
})
