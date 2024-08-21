package com.wespot.auth

object KakaoTemplateMapper {

    fun mapToJpaEntity(kakaoTemplate: KakaoTemplate): KakaoTemplateJpaEntity {
        return KakaoTemplateJpaEntity(
            id = kakaoTemplate.id,
            title = kakaoTemplate.title,
            description = kakaoTemplate.description,
            imageUrl = kakaoTemplate.imageUrl,
            buttonText = kakaoTemplate.buttonText,
            url = kakaoTemplate.url,
            type = kakaoTemplate.type
        )
    }

    fun mapToDomainEntity(kakaoTemplateJpaEntity: KakaoTemplateJpaEntity): KakaoTemplate {
        return KakaoTemplate(
            id = kakaoTemplateJpaEntity.id,
            title = kakaoTemplateJpaEntity.title,
            description = kakaoTemplateJpaEntity.description,
            imageUrl = kakaoTemplateJpaEntity.imageUrl,
            buttonText = kakaoTemplateJpaEntity.buttonText,
            url = kakaoTemplateJpaEntity.url,
            type = kakaoTemplateJpaEntity.type
        )
    }
}
