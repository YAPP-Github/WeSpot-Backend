package com.wespot.notification.dto

import com.wespot.notification.PublishNotificationType

class PublishNotificationTypeResponse(
    val possibleToPublishEvent: String
) {

    companion object {

        fun from(possibleToPublishEvent: PublishNotificationType) =
            PublishNotificationTypeResponse(possibleToPublishEvent.name)

    }
}
