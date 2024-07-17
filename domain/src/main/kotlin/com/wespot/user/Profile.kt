package com.wespot.user

data class Profile(
    val id: Long,
    val backgroundColor: String,
    val iconUrl: String,
) {

    companion object {
        fun create(
            backgroundColor: String,
            iconUrl: String
        ) =
            Profile(
                id = 0,
                backgroundColor = backgroundColor,
                iconUrl = iconUrl
            )
    }
}
