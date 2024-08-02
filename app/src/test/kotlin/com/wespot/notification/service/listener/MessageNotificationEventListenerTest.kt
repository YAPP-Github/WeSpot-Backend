package com.wespot.notification.service.listener

import com.wespot.notification.port.`in`.MessageNotificationUseCase
import com.wespot.notification.service.DisabledNotificationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MessageNotificationEventListenerTest @Autowired constructor(
    private val disabledNotificationService: DisabledNotificationService,
    private val messageNotificationService: MessageNotificationUseCase
) {

//    @EventListener
//    fun disableMessageNotificationByLimit(messageLimitEvent: MessageLimitEvent) {
//        disabledNotificationService.disableMessageNotification(
//            messageLimitEvent.messageId,
//            messageLimitEvent.sendMessageCount
//        )
//    }
//
//    @EventListener
//    fun receiveMessage(receivedMessageEvent: ReceivedMessageEvent) {
//        messageNotificationService.receiveMessage(receivedMessageEvent.receiver, receivedMessageEvent.messageId)
//    }
//
//    @EventListener
//    fun readMessageByReceiver(readMessageByReceiverEvent: ReadMessageByReceiverEvent) {
//        messageNotificationService.readMessageByReceiver(
//            readMessageByReceiverEvent.sender,
//            readMessageByReceiverEvent.messageId
//        )
//    }

}
