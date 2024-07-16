package com.wespot.user

import java.time.LocalDateTime

data class FCM(
    val id: Long,
    val user: User,
    val fcmToken: String?,
    val createdAt: LocalDateTime?,
) {
}
