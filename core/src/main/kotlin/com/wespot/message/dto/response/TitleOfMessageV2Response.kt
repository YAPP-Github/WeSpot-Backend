package com.wespot.message.dto.response

import com.wespot.user.User

data class TitleOfMessageV2Response(
    val title: String,
) {

    companion object {

        fun from(loginUser: User): TitleOfMessageV2Response {
            return TitleOfMessageV2Response("${loginUser.name}님을 설레게 한 친구에게\n쪽지로 마음을 표현해 보세요")
        }

    }

}
