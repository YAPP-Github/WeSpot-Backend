package com.wespot.user

data class Setting(
    val id: Long,
    val userId: Long,
    val isEnableNotification: Boolean,
) {
}