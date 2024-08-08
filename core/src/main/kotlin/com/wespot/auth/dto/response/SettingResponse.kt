package com.wespot.auth.dto.response

data class SettingResponse(
    val isVoteNotification: Boolean,
    val isMessageNotification: Boolean,
    val isMarketingNotification: Boolean
)
