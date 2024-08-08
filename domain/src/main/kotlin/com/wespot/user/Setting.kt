package com.wespot.user

data class Setting(
    val isEnableVoteNotification: Boolean = false,
    val isEnableMessageNotification: Boolean = false,
    val isEnableMarketingNotification: Boolean = false
)
