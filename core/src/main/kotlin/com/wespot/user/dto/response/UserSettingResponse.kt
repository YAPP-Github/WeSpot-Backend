package com.wespot.user.dto.response

import com.wespot.user.User

data class UserSettingResponse(
    val isEnableVoteNotification: Boolean,
    val isEnableMessageNotification: Boolean,
    val isEnableEventNotification: Boolean,
) {

    companion object {
        fun from(user: User) = UserSettingResponse(
            isEnableVoteNotification = user.isEnableVoteNotification(),
            isEnableMessageNotification = user.isEnableMessageNotification(),
            isEnableEventNotification = user.isEnableEventNotification()
        )
    }
}
