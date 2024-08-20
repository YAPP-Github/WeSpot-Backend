package com.wespot.auth.port.`in`

import com.wespot.auth.KakaoTemplateType
import com.wespot.auth.dto.request.KakaoTemplateRequest
import com.wespot.auth.dto.response.KakaoTemplateResponse

interface KakaoTemplateUseCase {

    fun getKakaoTemplate(type: KakaoTemplateType): KakaoTemplateResponse

}
