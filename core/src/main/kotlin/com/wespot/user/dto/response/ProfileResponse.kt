package com.wespot.user.dto.response

import com.wespot.user.Profile

data class ProfileResponse(
    val backgroundColor: String,
    val iconUrl: String
) {

    companion object {

        fun from(profile: Profile): ProfileResponse {
            return ProfileResponse(
                backgroundColor = profile.backgroundColor,
                iconUrl = profile.iconUrl
            )
        }

    }

}
