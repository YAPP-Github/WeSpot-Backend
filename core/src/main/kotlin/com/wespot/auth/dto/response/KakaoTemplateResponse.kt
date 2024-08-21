package com.wespot.auth.dto.response

import com.wespot.auth.KakaoTemplate
import com.wespot.auth.KakaoTemplateType

data class KakaoTemplateResponse(
    val id: Long,
    val type: KakaoTemplateType,
    val title: String,
    val description: String,
    val imageUrl: String,
    val buttonText: String,
    val url: String
) {
    companion object {
        fun from(
            kakaoTemplate: KakaoTemplate
        ): KakaoTemplateResponse {
            return KakaoTemplateResponse(
                id = kakaoTemplate.id,
                type = kakaoTemplate.type,
                title = kakaoTemplate.title,
                description = kakaoTemplate.description,
                imageUrl = kakaoTemplate.imageUrl,
                buttonText = kakaoTemplate.buttonText,
                url = kakaoTemplate.url
            )
        }
    }
}
