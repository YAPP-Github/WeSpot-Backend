package com.wespot.notification.port.`in`

import com.wespot.notification.dto.NotificationResponses

interface InquiryNotificationUseCase {

    fun getNotifications(cursorId: Long?, limit: Long): NotificationResponses

    fun readNotification(readNotificationId: Long)

}
