package com.wespot.notification.service.listener

import com.wespot.message.event.*
import com.wespot.notification.port.`in`.MessageNotificationUseCase
import com.wespot.notification.service.DisabledNotificationService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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

    private val logger: Logger = LoggerFactory.getLogger(MessageNotificationEventListener::class.java)

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun disableMessageNotificationByLimit(messageLimitEvent: MessageLimitEvent) {
        disabledNotificationService.disableMessageNotification(
            messageLimitEvent.senderId,
            messageLimitEvent.sendMessageCount
        )
    }


    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun receiveMessage(receivedMessageEvent: ReceivedMessageEventV1) {
        logger.info("Received Message V1: $receivedMessageEvent")

        messageNotificationService.receivedMessageV1(
            receiver = receivedMessageEvent.receiver,
            messageId = receivedMessageEvent.messageId
        )
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun receiveMessage(createdMessageEvent: CreatedMessageEvent) {
        logger.info("Received Message: $createdMessageEvent")

        messageNotificationService.receiveMessage(
            sender = createdMessageEvent.sender,
            senderAnonymousProfile = createdMessageEvent.senderAnonymousProfile,
            receiver = createdMessageEvent.receiver,
            receiverAnonymousProfile = createdMessageEvent.receiverAnonymousProfile,
            message = createdMessageEvent.message,
        )
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun readMessageByReceiver(readMessageByReceiverEvent: ReadMessageByReceiverEvent) {
        logger.info("Read message by receiver event: $readMessageByReceiverEvent")

        messageNotificationService.readMessageByReceiver(
            readMessageByReceiverEvent.sender,
            senderAnonymousProfile = null,
            readMessageByReceiverEvent.receiver,
            receiverAnonymousProfile = null,
            readMessageByReceiverEvent.messageId,
            readMessageByReceiverEvent.beforeIsReceiverRead
        )
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun answerMessage(messageAnswerEvent: MessageAnswerEvent) {
        logger.info("Answer message event: $messageAnswerEvent")

        messageNotificationService.answerMessage(
            sender = messageAnswerEvent.sender,
            senderAnonymousProfile = messageAnswerEvent.senderAnonymousProfile,
            receiver = messageAnswerEvent.receiver,
            receiverAnonymousProfile = messageAnswerEvent.receiverAnonymousProfile,
            message = messageAnswerEvent.message
        )
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun readMessageByReceiverV2(readMessageEvent: ReadMessageEvent) {
        logger.info("Read Message Event: $readMessageEvent")

        messageNotificationService.readMessageByReceiver(
            sender = readMessageEvent.sender,
            senderAnonymousProfile = readMessageEvent.senderAnonymousProfile,
            receiver = readMessageEvent.receiver,
            receiverAnonymousProfile = readMessageEvent.receiverAnonymousProfile,
            messageId = readMessageEvent.message.id,
            beforeIsReceiverRead = false
        )
    }

}
