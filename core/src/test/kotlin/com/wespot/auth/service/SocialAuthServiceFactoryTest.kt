package com.wespot.auth.service

import com.wespot.auth.service.kakao.KakaoService
import com.wespot.auth.service.apple.AppleService
import com.wespot.user.SocialType
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.every
import io.mockk.mockk

class SocialAuthServiceFactoryTest : BehaviorSpec({
    val kakaoService = mockk<KakaoService>()
    val appleService = mockk<AppleService>()
    val factory = SocialAuthServiceFactory(listOf(kakaoService, appleService))

    given("SocialAuthServiceFactory 테스트") {
        every { kakaoService.isSupport(SocialType.KAKAO) } returns true
        every { kakaoService.isSupport(SocialType.APPLE) } returns false

        every { appleService.isSupport(SocialType.KAKAO) } returns false
        every { appleService.isSupport(SocialType.APPLE) } returns true

        `when`("Kakao 소셜 타입에 대한 서비스를 요청할 때") {
            then("KakaoService를 반환해야 한다") {
                val service = factory.getService(SocialType.KAKAO)
                service shouldBe kakaoService
            }
        }

        `when`("Apple 소셜 타입에 대한 서비스를 요청할 때") {
            then("AppleService를 반환해야 한다") {
                val service = factory.getService(SocialType.APPLE)
                service shouldBe appleService
            }
        }
    }
})
