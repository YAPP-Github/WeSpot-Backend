package com.wespot.user.dto.response

import com.wespot.user.User

data class UserSettingResponse(
    val isEnableVoteNotification: Boolean,
    val isEnableMessageNotification: Boolean,
    val isEnableMarketingNotification: Boolean,
    val isEnablePostNotification: Boolean = true
) {

    companion object {
        fun from(user: User) = UserSettingResponse(
            isEnableVoteNotification = user.isEnableVoteNotification(),
            isEnableMessageNotification = user.isEnableMessageNotification(),
            isEnableMarketingNotification = user.isEnableMarketingNotification(),
            isEnablePostNotification = user.isEnablePostNotification()
        )
    }
}
