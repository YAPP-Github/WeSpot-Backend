package com.wespot.notification

import com.google.firebase.messaging.Notification
import java.time.LocalDate

data class NotificationInfo(
    val title: String,
    val date: LocalDate,
    val body: String,
    val targetId: Long,
    val userId: Long,
    val type: NotificationType,
) {

    companion object {

        fun createInitialState(
            notification: com.wespot.notification.Notification
        ) = NotificationInfo(
            title = notification.title,
            body = notification.body,
            targetId = notification.targetId,
            userId = notification.userId,
            date = notification.date,
            type = notification.type
        )

    }

    fun getNotification(): Notification {
        return Notification
            .builder()
            .setTitle(title)
            .setBody(body)
            .build()
    }

    fun getData(): Map<String, String> {
        val data = mutableMapOf<String, String>()
        data["targetId"] = targetId.toString()
        data["userId"] = userId.toString()
        data["date"] = date.toString()
        data["type"] = type.name

        return data
    }

}
