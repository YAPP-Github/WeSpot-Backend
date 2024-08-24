package com.wespot.user

data class Profile(
    val id: Long,
    val backgroundColor: String,
    val iconUrl: String,
) {

    fun update(
        backgroundColor: String?,
        iconUrl: String?
    ) = Profile(
        id = this.id,
        backgroundColor = backgroundColor ?: this.backgroundColor,
        iconUrl = iconUrl ?: this.iconUrl
    )

    companion object {

        const val INIT_PROFILE_ICON_URL = "https://wespot-test-data.s3.ap-northeast-2.amazonaws.com/wespot_init_profile.png"

        fun create(
            backgroundColor: String,
            iconUrl: String
        ) =
            Profile(
                id = 0,
                backgroundColor = backgroundColor,
                iconUrl = iconUrl
            )

        fun createInit() =
            Profile(
                id = 0,
                backgroundColor = "",
                iconUrl = INIT_PROFILE_ICON_URL
            )

    }
}
