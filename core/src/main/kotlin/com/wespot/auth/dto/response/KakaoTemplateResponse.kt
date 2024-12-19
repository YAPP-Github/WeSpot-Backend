package com.wespot.auth.dto.response

import com.wespot.auth.KakaoTemplate
import com.wespot.auth.KakaoTemplateType
import com.wespot.user.User

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

        private val LOGIN_USER_NAME_PLACE_HOLDER = "{name}"

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

        fun ofWithUser(
            kakaoTemplate: KakaoTemplate,
            loginUser: User
        ): KakaoTemplateResponse {
            return KakaoTemplateResponse(
                id = kakaoTemplate.id,
                type = kakaoTemplate.type,
                title = kakaoTemplate.title.replace(LOGIN_USER_NAME_PLACE_HOLDER, loginUser.name),
                description = kakaoTemplate.description,
                imageUrl = kakaoTemplate.imageUrl,
                buttonText = kakaoTemplate.buttonText,
                url = kakaoTemplate.url
            )
        }

    }

}
