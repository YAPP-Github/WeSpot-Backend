package com.wespot.notification.service

import com.wespot.notification.message.DisabledMessageNotificationByLimitService
import com.wespot.notification.port.out.NotificationPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DisabledNotificationServiceTest @Autowired constructor(
    private val notificationPort: NotificationPort,
    private val disabledMessageNotificationByLimitService: DisabledMessageNotificationByLimitService
) {

//    @Transactional
//    override fun disableVoteNotifications(today: LocalDate) {
//        val notifications = NotificationFinder.findAllFromDateYesterday(notificationPort, today)
//        notifications.forEach { it.disableVoteNotification(today) }
//        notificationPort.saveAll(notifications)
//    }
//
//    @Transactional
//    override fun disableMessageNotifications(today: LocalDate) {
//        val notifications = NotificationFinder.findAllFromDate(notificationPort, today)
//        notifications.forEach { it.disableMessageNotification() }
//        notificationPort.saveAll(notifications)
//    }
//
//    @Transactional
//    override fun disableMessageNotification(messageId: Long, sendMessageCount: Int) {
//        val notifications = disabledMessageNotificationByLimitService.disableMessageNotification(
//            messageId,
//            sendMessageCount
//        ) { NotificationFinder.findAllByTargetId(notificationPort, messageId) }
//        notificationPort.saveAll(notifications)
//    }

}
