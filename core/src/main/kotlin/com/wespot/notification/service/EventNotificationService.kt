package com.wespot.notification.service

import com.wespot.notification.LatestVersionType
import com.wespot.notification.NotificationType
import com.wespot.notification.dto.NotificationPublishingRequest
import com.wespot.notification.port.`in`.PublishNotificationUseCase
import com.wespot.notification.port.out.LatestVersionPort
import com.wespot.notification.port.out.NotificationPort
import com.wespot.notification.vote.ProfileUpdateNotificationService
import com.wespot.user.port.out.UserPort
import com.wespot.user.port.out.UserVersionPort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class EventNotificationService(
    val userPort: UserPort,
    val userVersionPort: UserVersionPort,
    val notificationPort: NotificationPort,
    val latestVersionPort: LatestVersionPort,
    val profileUpdateNotificationService: ProfileUpdateNotificationService,
    val notificationHelper: NotificationHelper
) : PublishNotificationUseCase {

    @Transactional
    override fun publishProfileUpdate(notificationPublishingRequest: NotificationPublishingRequest) {
        val androidLatestVersion = latestVersionPort.get(LatestVersionType.ANDROID)
        val iosLatestVersion = latestVersionPort.get(LatestVersionType.IOS)
        val users = userPort.findAll()
        val userVersions = userVersionPort.findAll()

        val notifications = profileUpdateNotificationService.getNotifications(
            users = users,
            userVersions = userVersions,
            notificationType = NotificationType.PROFILE_UPDATE,
            androidLatestVersion = androidLatestVersion,
            iosLatestVersion = iosLatestVersion,
            title = notificationPublishingRequest.title,
            body = notificationPublishingRequest.body
        )

        notificationPort.saveAll(notifications)
        notificationHelper.sendNotifications(users, notifications)
    }

}
