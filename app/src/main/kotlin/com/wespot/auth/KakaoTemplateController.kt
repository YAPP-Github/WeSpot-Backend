package com.wespot.auth

import com.wespot.auth.dto.response.KakaoTemplateResponse
import com.wespot.auth.port.`in`.KakaoTemplateUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class KakaoTemplateController (
    private val kakaoTemplateUseCase: KakaoTemplateUseCase
){
    @GetMapping("/kakao/template")
    fun getKakaoTemplate(
        @RequestParam type: String
    ): KakaoTemplateResponse {
        val kakaoTemplateType = KakaoTemplateType.valueOf(type.uppercase())
        return kakaoTemplateUseCase.getKakaoTemplate(kakaoTemplateType)
    }

}
