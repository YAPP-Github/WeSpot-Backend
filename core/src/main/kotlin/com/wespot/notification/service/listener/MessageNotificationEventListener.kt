package com.wespot.notification.service.listener

import com.wespot.message.event.MessageLimitEvent
import com.wespot.message.event.ReadMessageByReceiverEvent
import com.wespot.message.event.ReceivedMessageEvent
import com.wespot.notification.port.`in`.MessageNotificationUseCase
import com.wespot.notification.service.DisabledNotificationService
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class MessageNotificationEventListener(
    private val disabledNotificationService: DisabledNotificationService,
    private val messageNotificationService: MessageNotificationUseCase,
) {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    fun disableMessageNotificationByLimit(messageLimitEvent: MessageLimitEvent) {
        disabledNotificationService.disableMessageNotification(
            messageLimitEvent.senderId,
            messageLimitEvent.sendMessageCount
        )
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    fun receiveMessage(receivedMessageEvent: ReceivedMessageEvent) {
        messageNotificationService.receiveMessage(receivedMessageEvent.receiver, receivedMessageEvent.messageId)
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    fun readMessageByReceiver(readMessageByReceiverEvent: ReadMessageByReceiverEvent) {
        messageNotificationService.readMessageByReceiver(
            readMessageByReceiverEvent.sender,
            readMessageByReceiverEvent.receiver,
            readMessageByReceiverEvent.messageId,
            readMessageByReceiverEvent.beforeIsReceiverRead
        )
    }

}
