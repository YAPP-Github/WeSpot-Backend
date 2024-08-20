package com.wespot.auth

import com.wespot.auth.port.out.KakaoTemplatePort
import org.springframework.stereotype.Repository

@Repository
class KakaoTemplatePersistentAdapter(
    private val kakaoTemplateJpaRepository: KakaoTemplateJpaRepository
) : KakaoTemplatePort {

    override fun getKakaoTemplate(type: KakaoTemplateType): KakaoTemplate? {
        return kakaoTemplateJpaRepository.findByType(type)
            ?.let { KakaoTemplateMapper.mapToDomainEntity(it) }
    }


}
