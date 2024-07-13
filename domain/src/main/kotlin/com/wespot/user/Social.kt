package com.wespot.user

data class Social(
    val socialType: SocialType,
    val socialId: Long,
    val socialRefreshToken: String
) {
}