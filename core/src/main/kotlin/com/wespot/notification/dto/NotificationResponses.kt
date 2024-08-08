package com.wespot.notification.dto

import com.wespot.notification.Notification

data class NotificationResponses(
    val notifications: List<NotificationResponse>,
    val hasNext: Boolean
) {

    companion object {

        fun from(notifications: List<Notification>, hasNext: Boolean): NotificationResponses {
            val response: List<NotificationResponse> = notifications.stream()
                .map { NotificationResponse.from(it) }
                .toList()

            return NotificationResponses(response, hasNext)
        }

    }

}
