package com.wespot.notification.service

import com.wespot.notification.message.DisabledMessageNotificationByLimitService
import com.wespot.notification.port.`in`.DisabledNotificationUseCase
import com.wespot.notification.port.out.NotificationPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class DisabledNotificationService(
    private val notificationPort: NotificationPort,
    private val disabledMessageNotificationByLimitService: DisabledMessageNotificationByLimitService
) : DisabledNotificationUseCase {

    @Transactional
    override fun disableVoteNotifications(today: LocalDate) {
        val notifications = NotificationFinder.findAllFromDateYesterday(notificationPort, today)
        notifications.forEach { it.disableVoteNotification(today) }
        notificationPort.saveAll(notifications)
    }

    @Transactional
    override fun disableMessageNotifications(today: LocalDate) {
        val notifications = NotificationFinder.findAllFromDate(notificationPort, today)
        notifications.forEach { it.disableMessageNotification() }
        notificationPort.saveAll(notifications)
    }

    @Transactional
    override fun disableMessageNotification(messageId: Long, sendMessageCount: Int) {
        val notifications = disabledMessageNotificationByLimitService.disableMessageNotification(
            messageId,
            sendMessageCount
        ) { NotificationFinder.findAllByTargetId(notificationPort, messageId) }
        notificationPort.saveAll(notifications)
    }

}
