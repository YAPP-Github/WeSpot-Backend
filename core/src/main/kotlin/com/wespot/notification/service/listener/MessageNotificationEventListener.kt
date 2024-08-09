package com.wespot.notification.service.listener

import com.wespot.message.event.MessageLimitEvent
import com.wespot.message.event.ReadMessageByReceiverEvent
import com.wespot.message.event.ReceivedMessageEvent
import com.wespot.notification.port.`in`.MessageNotificationUseCase
import com.wespot.notification.service.DisabledNotificationService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class MessageNotificationEventListener(
    private val disabledNotificationService: DisabledNotificationService,
    private val messageNotificationService: MessageNotificationUseCase,
) {

    @EventListener
    fun disableMessageNotificationByLimit(messageLimitEvent: MessageLimitEvent) {
        disabledNotificationService.disableMessageNotification(
            messageLimitEvent.senderId,
            messageLimitEvent.sendMessageCount
        )
    }

    @EventListener
    fun receiveMessage(receivedMessageEvent: ReceivedMessageEvent) {
        messageNotificationService.receiveMessage(receivedMessageEvent.receiver, receivedMessageEvent.messageId)
    }

    @EventListener
    fun readMessageByReceiver(readMessageByReceiverEvent: ReadMessageByReceiverEvent) {
        messageNotificationService.readMessageByReceiver(
            readMessageByReceiverEvent.sender,
            readMessageByReceiverEvent.receiver,
            readMessageByReceiverEvent.messageId,
            readMessageByReceiverEvent.beforeIsReceiverRead
        )
    }

}
