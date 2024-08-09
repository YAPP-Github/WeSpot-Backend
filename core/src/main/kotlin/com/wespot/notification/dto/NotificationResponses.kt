package com.wespot.notification.dto

import com.wespot.notification.Notification

data class NotificationResponses(
    val notifications: List<NotificationResponse>,
    val lastCursorId: Long,
    val hasNext: Boolean
) {

    companion object {

        fun from(notifications: List<Notification>, hasNext: Boolean): NotificationResponses {
            val response: List<NotificationResponse> = notifications.stream()
                .map { NotificationResponse.from(it) }
                .toList()

            return NotificationResponses(response, getLastCursorId(notifications), hasNext)
        }

        private fun getLastCursorId(notifications: List<Notification>): Long {
            if (notifications.isEmpty()) {
                return 0
            }

            return notifications.last()
                .id
        }

    }

}
