package com.wespot.notification.port.`in`

import com.wespot.notification.dto.NotificationPublishingRequest

interface PublishNotificationUseCase {

    fun publishProfileUpdate(notificationPublishingRequest: NotificationPublishingRequest)

}
