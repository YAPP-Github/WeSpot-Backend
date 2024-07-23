package com.wespot.vote.dto.response.top5

import com.wespot.user.User
import com.wespot.vote.dto.response.VoteProfileResponse

data class VoteUserResponseOfTop5(
    val id: Long,
    val name: String,
    val introduction: String,
    val profile: VoteProfileResponse
) {

    companion object {

        fun from(user: User): VoteUserResponseOfTop5 {
            return VoteUserResponseOfTop5(
                user.id,
                user.name,
                user.introduction,
                VoteProfileResponse.from(user.profile)
            )
        }

    }

}
