package com.wespot.user.dto.response

import com.wespot.user.ProfileIcon

data class CharacterResponse(
    val id: Long,
    val name: String,
    val iconUrl: String
) {

    companion object {

        fun from(profileIcon: ProfileIcon) =
            CharacterResponse(
                id = profileIcon.id,
                name = profileIcon.name,
                iconUrl = profileIcon.iconUrl
            )

    }

}
