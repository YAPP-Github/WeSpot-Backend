package com.wespot.user.dto.request

data class ModifiedSettingRequest(
    val isEnableVoteNotification: Boolean? = null,
    val isEnableMessageNotification: Boolean? = null,
    val isEnableMarketingNotification: Boolean? = null,
    val isEnablePostNotification: Boolean? = null,
)
