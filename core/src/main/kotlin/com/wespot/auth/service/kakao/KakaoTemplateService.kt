package com.wespot.auth.service.kakao

import com.wespot.auth.KakaoTemplateType
import com.wespot.auth.dto.response.KakaoTemplateResponse
import com.wespot.auth.port.`in`.KakaoTemplateUseCase
import com.wespot.auth.port.out.KakaoTemplatePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class KakaoTemplateService(
    private val kakaoTemplatePort: KakaoTemplatePort
) : KakaoTemplateUseCase {

    override fun getKakaoTemplate(type: KakaoTemplateType): KakaoTemplateResponse {
        val kakaoTemplate = kakaoTemplatePort.getKakaoTemplate(type = type)
            ?: throw NoSuchElementException("해당 타입에는 카카오 템플릿이 존재하지 않습니다.")
        return KakaoTemplateResponse.from(kakaoTemplate)
    }

}
