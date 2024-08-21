package com.wespot.auth.service

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.SocialType
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class SocialAuthServiceFactory(
    private val services: List<SocialAuthService>
) {
    fun getService(socialType: SocialType): SocialAuthService {
        return services.find { it.isSupport(socialType) }
            ?: throw CustomException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ExceptionView.TOAST,
                "해당 소셜로그인을 지원하지 않습니다. social type: $socialType"
            )
    }
}
