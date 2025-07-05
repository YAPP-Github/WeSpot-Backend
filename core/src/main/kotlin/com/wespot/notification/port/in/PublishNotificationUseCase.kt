package com.wespot.notification.port.`in`

import com.wespot.notification.dto.NotificationPublishingRequest
import com.wespot.notification.dto.PublishNotificationTypeResponse

interface PublishNotificationUseCase {

    fun viewAllOfPossibleToPublishTypes(): List<PublishNotificationTypeResponse>

    fun publish(notificationPublishingRequest: NotificationPublishingRequest)

}
