package com.wespot.vote.dto.response.top1

import com.wespot.user.User

data class UserResponseOfTop1(
    val id: Long,
    val name: String,
    val introduction: String,
    val iconUrl: String,
) {

    companion object {

        fun from(user: User): UserResponseOfTop1 {
            return UserResponseOfTop1(
                user.id,
                user.name,
                user.introduction,
                user.profile.iconUrl
            )
        }

    }

}
