package com.wespot.notification.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class NotificationServiceHelperTest @Autowired constructor(
    private val notificationSendService: NotificationSendService,
) {

//    fun sendMulticastNotification(users: List<User>, notifications: List<Notification>) {
//        if (notifications.isEmpty()) {
//            return
//        }
//
//        val notificationUserIdGroup = notifications.map { it.userId }.toHashSet()
//        val notificationUsers = users.filter { notificationUserIdGroup.contains(it.id) }
//        val notificationInfo = getNotificationInfoBy(notifications[0])
//        notificationSendService.sendMulticastNotification(notificationUsers, notificationInfo)
//    }
//
//    private fun getNotificationInfoBy(notification: Notification): NotificationInfo {
//        if (notification.isVoteType()) {
//            return NotificationInfo.createVoteInitialState(
//                title = notification.content,
//                body = "아직 미정",
//                date = notification.date,
//                type = notification.type
//            )
//        }
//        return NotificationInfo.createMessageInitialState(
//            title = notification.content,
//            body = "아직 미정",
//            targetId = notification.targetId,
//            type = notification.type
//        )
//    }
//
//    fun sendNotification(user: User, notification: Notification) {
//        notificationSendService.sendNotification(
//            user,
//            getNotificationInfoBy(notification)
//        )
//    }

}
