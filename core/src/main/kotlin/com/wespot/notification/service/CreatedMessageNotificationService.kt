package com.wespot.notification.service

import com.wespot.notification.message.OpenMessageNotificationService
import com.wespot.notification.message.ReadMessageByReceiverService
import com.wespot.notification.message.ReceivedMessageNotificationService
import com.wespot.notification.port.`in`.MessageNotificationUseCase
import com.wespot.notification.port.out.NotificationPort
import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreatedMessageNotificationService(
    private val userPort: UserPort,
    private val notificationPort: NotificationPort,
    private val openMessageNotificationService: OpenMessageNotificationService,
    private val receivedMessageNotificationService: ReceivedMessageNotificationService,
    private val readMessageByReceiverService: ReadMessageByReceiverService,
    private val notificationServiceHelper: NotificationServiceHelper
) : MessageNotificationUseCase {

    @Transactional
    override fun openMessage() {
        val users = userPort.findAll()
        val notifications = openMessageNotificationService.getNotifications(users)
        notificationPort.saveAll(notifications)
        notificationServiceHelper.sendMulticastNotification(users, notifications)
    }

    @Transactional
    override fun receiveMessage(receiver: User, messageId: Long) {
        val notification = receivedMessageNotificationService.getNotification(receiver, messageId)
        notificationPort.save(notification)
        notificationServiceHelper.sendNotification(receiver, notification)
    }

    @Transactional
    override fun readMessageByReceiver(sender: User, messageId: Long) {
        val notification = readMessageByReceiverService.getNotification(sender, messageId)
        notificationPort.save(notification)
        notificationServiceHelper.sendNotification(sender, notification)
    }

}
