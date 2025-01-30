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

        fun emptyFCM(): FCM {
            return FCM(
                id = 0,
                fcmToken = null,
                createdAt = null,
            )
        }
    }

    fun update(fcmToken: String?): FCM {
        fcmToken ?: return this

        return FCM(
            id = id,
            fcmToken = fcmToken,
            createdAt = createdAt,
        )
    }

    fun clearFcmToken(): FCM {
        return FCM(
            id = id,
            fcmToken = null,
            createdAt = createdAt
        )
    }

}
