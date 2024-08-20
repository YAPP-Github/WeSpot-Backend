package com.wespot.auth

import org.springframework.data.jpa.repository.JpaRepository

interface KakaoTemplateJpaRepository: JpaRepository<KakaoTemplateJpaEntity, Long> {

    fun findByType(type: KakaoTemplateType): KakaoTemplateJpaEntity?

}
