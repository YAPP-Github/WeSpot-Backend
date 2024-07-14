package com.wespot.vote.dto

import com.wespot.user.Profile

class ProfileResponse(
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