package com.wespot.vote.dto.response

import com.wespot.user.Profile

data class VoteProfileResponse(
    val backgroundColor: String,
    val iconUrl: String
) {

    companion object {

        fun from(profile: Profile): VoteProfileResponse {
            return VoteProfileResponse(
                backgroundColor = profile.backgroundColor,
                iconUrl = profile.iconUrl
            )
        }

    }

}
