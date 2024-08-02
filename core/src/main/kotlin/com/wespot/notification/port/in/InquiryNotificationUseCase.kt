package com.wespot.notification.port.`in`

import com.wespot.notification.dto.NotificationResponses

interface InquiryNotificationUseCase {

    fun getNotifications(): NotificationResponses

    fun readNotification(readNotificationId: Long)

}
