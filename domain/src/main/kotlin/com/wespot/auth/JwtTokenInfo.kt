package com.wespot.auth

object JwtTokenInfo {
    const val AUTHORIZATION_HEADER = "Authorization"
    const val BEARER_TYPE = "Bearer"
    const val EMAIL_CLAIM: String = "email"
    const val ACCESS_TOKEN: String = "AccessToken"
    const val REFRESH_TOKEN: String = "RefreshToken"
    const val REFRESH_TOKEN_EXPIRY_DAYS = 30L
}