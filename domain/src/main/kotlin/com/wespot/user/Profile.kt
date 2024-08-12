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
                iconUrl = ""
            )

    }
}
