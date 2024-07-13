package com.wespot.user

data class Social(
    val socialType: SocialType,
    val socialId: Long,
    val socialEmail: String?,
    val socialRefreshToken: String
) {
}
