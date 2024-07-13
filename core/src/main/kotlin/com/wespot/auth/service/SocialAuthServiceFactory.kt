package com.wespot.auth.service

import com.wespot.user.SocialType
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
class SocialAuthServiceFactory(
    private val services: List<SocialAuthService>
) {
    fun getService(socialType: SocialType): SocialAuthService {
        return services.find { it.isSupport(socialType) }
            ?: throw IllegalArgumentException("해당 소셜로그인을 지원하지 않습니다. social type: $socialType")
    }
}
