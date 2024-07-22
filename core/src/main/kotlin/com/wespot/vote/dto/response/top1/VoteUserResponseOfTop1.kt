package com.wespot.vote.dto.response.top1

import com.wespot.user.User

data class VoteUserResponseOfTop1(
    val id: Long,
    val name: String,
    val introduction: String,
    val iconUrl: String,
) {

    companion object {

        fun from(user: User): VoteUserResponseOfTop1 {
            return VoteUserResponseOfTop1(
                user.id,
                user.name,
                user.introduction,
                user.profile.iconUrl
            )
        }

    }

}
