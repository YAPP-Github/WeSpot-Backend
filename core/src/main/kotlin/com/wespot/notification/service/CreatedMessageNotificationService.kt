package com.wespot.notification.service

import com.wespot.message.v2.MessageV2
import com.wespot.notification.message.MessageAnswerNotificationService
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
    private val messageAnswerNotificationService: MessageAnswerNotificationService,
    private val notificationHelper: NotificationHelper
) : MessageNotificationUseCase {

    @Transactional
    override fun openMessage() {
        val users = userPort.findAll()
        val notifications = openMessageNotificationService.getNotifications(users)
        notificationPort.saveAll(notifications)
        val usersGroup = users.associateBy { it.id }
        notifications.forEach { notificationHelper.sendNotification(usersGroup[it.userId], it) }
    }

    @Transactional
    override fun receiveMessage(receiver: User, messageId: Long) {
        val notification = receivedMessageNotificationService.getNotification(receiver.id, receiver.name, messageId)
        notificationPort.save(notification)
        notificationHelper.sendNotification(receiver, notification)
    }

    @Transactional
    override fun readMessageByReceiver(sender: User, receiver: User, messageId: Long, beforeIsReceiverRead: Boolean) {
        val notification =
            readMessageByReceiverService.getNotification(sender.id, receiver.name, messageId, beforeIsReceiverRead)
                ?: return
        notificationPort.save(notification)
        notificationHelper.sendNotification(sender, notification)
    }

    @Transactional
    override fun answerMessage(sender: User, receiver: User, message: MessageV2) {
        val notification =
            messageAnswerNotificationService.getNotification(sender = sender, receiver = receiver, message = message)
        notificationPort.save(notification)
        notificationHelper.sendNotification(receiver = receiver, notification = notification)
    }

}
