package com.wespot.notification.dto

import com.wespot.notification.Notification

data class NotificationResponse(
    val id: Long,
    val type: String,
    val date: String,
    val targetId: Long,
    val content: String,
    val isNew: Boolean,
    val isEnable: Boolean,
    val createdAt: String,
) {

    companion object {

        fun from(notification: Notification): NotificationResponse {
            return NotificationResponse(
                id = notification.id,
                type = notification.type.name,
                date = notification.date.toString(),
                targetId = notification.targetId,
                content = notification.title,
                isNew = !notification.isRead,
                isEnable = notification.isEnabled,
                createdAt = notification.createdAt.toString()
            )
        }

    }

}
