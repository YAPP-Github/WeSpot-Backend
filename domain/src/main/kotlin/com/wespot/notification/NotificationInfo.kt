package com.wespot.notification

import com.google.firebase.messaging.Notification
import java.time.LocalDate

class NotificationInfo(
    val title: String,
    val body: String,
    val targetId: Long,
    val date: LocalDate,
    val type: NotificationType,
) {

    companion object {

        fun createVoteInitialState(
            title: String,
            body: String,
            date: LocalDate,
            type: NotificationType
        ) = NotificationInfo(
            title = title,
            body = body,
            targetId = 0,
            date = date,
            type = type
        )

        fun createMessageInitialState(
            title: String,
            body: String,
            targetId: Long,
            type: NotificationType
        ) = NotificationInfo(
            title = title,
            body = body,
            targetId = targetId,
            date = LocalDate.now(),
            type = type
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
        data["date"] = date.toString()
        data["type"] = type.name

        return data
    }

}
