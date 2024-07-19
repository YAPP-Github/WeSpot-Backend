package com.wespot.user.dto.response

import com.wespot.user.ProfileBackground

data class BackgroundResponse(
    val id: Long,
    val name: String,
    val color: String
) {

    companion object {

        fun from(profileBackground: ProfileBackground) =
            BackgroundResponse(
                id = profileBackground.id,
                name = profileBackground.name,
                color = profileBackground.backgroundColor
            )

    }

}

