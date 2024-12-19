package com.wespot.auth.dto.request

import com.wespot.user.SocialType

data class AuthLoginRequest(
    val socialType: SocialType,
    val authorizationCode: String?,
    val identityToken: String?,
    val fcmToken: String?,
    val androidVersionName: String?,
    val iosVersionName: String?
)
