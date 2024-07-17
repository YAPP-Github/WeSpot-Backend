package com.wespot.user

data class Social(
    val socialType: SocialType,
    val socialId: String,
    val socialEmail: String?,
    val socialRefreshToken: String?
) {

    companion object{
        fun create(
            email : String,
            socialEmail: String,
            socialRefreshToken: String
        ): Social {
            return Social(
                socialType = SocialType.valueOf(email.split("@")[1]),
                socialId = email.split("@")[0],
                socialEmail = socialEmail,
                socialRefreshToken = socialRefreshToken
            )
        }
    }
}
