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
        val notifications = NotificationFinder.findAllYesterdayFromDate(notificationPort, today)
        val disabledNotificationsAtTwoDaysAgo =
            NotificationFinder.findAllYesterdayFromDate(notificationPort, today.minusDays(1))
        notifications.forEach { it.disableVoteNotification(today) }
        notifications.forEach { it.disableVoteNotification(today.minusDays(1)) }
        notificationPort.saveAll(notifications)
    }

    @Transactional
    override fun disableMessageNotifications(today: LocalDate) {
        val notifications = NotificationFinder.findAllFromDate(notificationPort, today)
        notifications.forEach { it.disableMessageNotification() }
        notificationPort.saveAll(notifications)
    }

    @Transactional
    override fun disableMessageNotification(senderId: Long, sendMessageCount: Int) {
        val today = LocalDate.now()
        val notifications = disabledMessageNotificationByLimitService.disableMessageNotification(
            sendMessageCount
        ) { NotificationFinder.findAllByUserIdAndFromDate(notificationPort, senderId, today) }
        notificationPort.saveAll(notifications)
    }

}
