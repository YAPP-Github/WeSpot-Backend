package com.wespot.notification

import com.wespot.user.UserVersion
import java.util.*

enum class PublishNotificationType(
    val updatedUserNotificationType: NotificationType,
    val notUpdatedUserNotificationType: NotificationType
) {

    PROFILE_UPDATE(NotificationType.PROFILE_UPDATE, NotificationType.UPDATE_REQUIRED),
    ;

    fun getNotificationTypeByUserVersion(
        userVersion: UserVersion?,
        androidLatestVersion: String,
        iosLatestVersion: String,
    ): NotificationType {
        if (Objects.isNull(userVersion)) {
            return notUpdatedUserNotificationType
        }

        if (userVersion!!.hasLatestVersion(androidLatestVersion, iosLatestVersion)) {
            return updatedUserNotificationType
        }

        return notUpdatedUserNotificationType
    }

}
