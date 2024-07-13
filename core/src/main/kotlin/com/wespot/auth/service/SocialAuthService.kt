package com.wespot.auth.service

import com.wespot.auth.dto.AuthLoginRequest
import com.wespot.auth.dto.OAuthIdAndRefreshToken
import com.wespot.user.SocialType

interface SocialAuthService {
    fun fetchAuthToken(authLoginRequest: AuthLoginRequest): OAuthIdAndRefreshToken
    fun isSupport(socialType: SocialType): Boolean
    fun revoke(socialId: String, socialRefreshToken: String?): Boolean
}
