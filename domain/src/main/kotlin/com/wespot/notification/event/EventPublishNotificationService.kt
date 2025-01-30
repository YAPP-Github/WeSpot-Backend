package com.wespot.notification.event

import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import com.wespot.notification.PublishNotificationType
import com.wespot.user.User
import com.wespot.user.UserVersion
import org.springframework.stereotype.Component

@Component
class EventPublishNotificationService {

    fun getNotifications(
        users: List<User>,
        userVersions: List<UserVersion>,
        androidLatestVersion: String,
        iosLatestVersion: String,
        publishNotificationType: PublishNotificationType,
        title: String,
        body: String,
    ): List<Notification> {
        val userVersionGroup = userVersions.associateBy { it.userId }

        return users.stream()
            .map {
                Notification.createEventInitialState(
                    it.id,
                    publishNotificationType.getNotificationTypeByUserVersion(
                        userVersionGroup[it.id],
                        androidLatestVersion,
                        iosLatestVersion
                    ),
                    title,
                    body
                )
            }.toList()
    }

}
