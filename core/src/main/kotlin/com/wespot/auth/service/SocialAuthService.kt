package com.wespot.auth.service

import com.wespot.auth.dto.AuthLoginRequest
import com.wespot.auth.dto.SocialResponse
import com.wespot.user.SocialType

interface SocialAuthService {

    fun fetchAuthToken(authLoginRequest: AuthLoginRequest): SocialResponse

    fun isSupport(socialType: SocialType): Boolean

    fun revoke(socialId: String, socialRefreshToken: String?): Boolean

}
