package com.wespot.user.dto.request

data class ModifiedSettingRequest(
    val isEnableVoteNotification: Boolean,
    val isEnableMessageNotification: Boolean
)
