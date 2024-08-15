package com.wespot.user

import java.time.LocalDateTime

data class FCM(
    val id: Long,
    val fcmToken: String?,
    val createdAt: LocalDateTime?,
) {

    companion object {
        fun from(fcmToken: String?): FCM {
            fcmToken ?: return FCM(
                id = 0,
                fcmToken = null,
                createdAt = null,
            )

            return FCM(
                id = 0,
                fcmToken = fcmToken,
                createdAt = LocalDateTime.now(),
            )
        }
    }

}
