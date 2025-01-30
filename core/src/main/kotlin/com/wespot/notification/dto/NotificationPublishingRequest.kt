package com.wespot.notification.dto

import com.wespot.notification.PublishNotificationType

class NotificationPublishingRequest(
    val publishNotificationType: PublishNotificationType,
    val title: String,
    val body: String,
) {

}
