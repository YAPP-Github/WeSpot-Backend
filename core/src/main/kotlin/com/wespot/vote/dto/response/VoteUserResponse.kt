package com.wespot.vote.dto.response

import com.wespot.user.User
import com.wespot.user.dto.response.ProfileResponse

data class VoteUserResponse(
    val id: Long,
    val name: String,
    val introduction: String,
    val profile: ProfileResponse
) {

    companion object {

        fun from(user: User): VoteUserResponse {
            return VoteUserResponse(
                id = user.id,
                name = user.name,
                introduction = user.introduction,
                profile = user.profile.let { ProfileResponse.from(it) }
            )
        }

    }

}
