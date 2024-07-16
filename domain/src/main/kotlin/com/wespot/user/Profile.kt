package com.wespot.user

data class Profile(
    val id: Long,
    val user: User,
    val backgroundColor: String,
    val iconUrl: String,
) {

    companion object {
        fun create(
            user: User,
            backgroundColor: String,
            iconUrl: String
        ) =
            Profile(
                id = 0,
                user = user,
                backgroundColor = backgroundColor,
                iconUrl = iconUrl
            )
    }
}
