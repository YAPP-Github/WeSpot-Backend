package com.wespot.vote.dto.response.top5

import com.wespot.user.User
import com.wespot.user.dto.response.ProfileResponse

data class UserResponseOfTop5(
    val id: Long,
    val name: String,
    val introduction: String,
    val profile: ProfileResponse
) {

    companion object {

        fun from(user: User): UserResponseOfTop5 {
            return UserResponseOfTop5(
                user.id,
                user.name,
                user.introduction,
                ProfileResponse.from(user.profile)
            )
        }

    }

}
