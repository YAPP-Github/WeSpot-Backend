package com.wespot.user

import java.time.LocalDateTime

data class FCM(
    val id: Long,
    val userId: Long,
    val fcmToken: String,
    val createdAt: LocalDateTime,
) {
}