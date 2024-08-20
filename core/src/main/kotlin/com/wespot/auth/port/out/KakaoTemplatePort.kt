package com.wespot.auth.port.out

import com.wespot.auth.KakaoTemplate
import com.wespot.auth.KakaoTemplateType

interface KakaoTemplatePort {

    fun getKakaoTemplate(type: KakaoTemplateType): KakaoTemplate?

}
