package com.wespot.notification.vote

import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import com.wespot.user.User
import com.wespot.user.UserVersion
import org.springframework.stereotype.Component

@Component
class ProfileUpdateNotificationService {

    fun getNotifications(
        users: List<User>,
        userVersions: List<UserVersion>,
        notificationType: NotificationType,
        androidLatestVersion: String,
        iosLatestVersion: String,
        title: String,
        body: String,
    ): List<Notification> {
        val userGroup = users.associateBy { it.id }
        return userVersions.filter { it.isPossibleToSendUpdateNotification(androidLatestVersion, iosLatestVersion) }
            .filter { userGroup[it.userId] != null }
            .map {
                Notification.createEventInitialState(
                    it.userId,
                    NotificationType.PROFILE_UPDATE,
                    title,
                    body
                )
            }
    }

}
