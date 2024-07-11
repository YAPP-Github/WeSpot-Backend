package com.wespot.auth.service

import com.wespot.user.SocialType
import org.springframework.stereotype.Component

@Component
class SocialAuthServiceFactory(
    private val services: List<SocialAuthService>
) {
    fun getService(socialType: SocialType): SocialAuthService {
        return services.find { it.isSupport(socialType) }
            ?: throw IllegalArgumentException("Unsupported social type: $socialType")
    }
}
