package com.wespot.auth.service

import com.wespot.auth.dto.request.AuthLoginRequest
import com.wespot.auth.dto.response.SocialResponse
import com.wespot.user.SocialType

interface SocialAuthService {

    fun fetchAuthToken(authLoginRequest: AuthLoginRequest): SocialResponse

    fun isSupport(socialType: SocialType): Boolean

    fun revoke(socialId: String, socialRefreshToken: String?): Boolean

}
