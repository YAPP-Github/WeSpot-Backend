package com.wespot.vote.dto.response

import com.wespot.user.User

data class UserResponse(
    val id: Long,
    val name: String,
    val profile: ProfileResponse?
) {

    companion object {

        fun from(user: User): UserResponse {
            return UserResponse(
                id = user.id,
                name = user.name,
                profile = null
            )
        }

    }

}
