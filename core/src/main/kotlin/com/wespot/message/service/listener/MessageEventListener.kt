package com.wespot.message.service.listener

import com.wespot.message.event.MessageV2BlockedEvent
import com.wespot.message.port.`in`.CreateMessageUseCase
import com.wespot.message.port.`in`.CreatedMessageV2UseCase
import com.wespot.user.block.BlockedUser
import com.wespot.user.event.WelcomeMessageEvent
import com.wespot.user.port.out.BlockedUserPort
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class MessageEventListener(
    private val createMessageUseCase: CreateMessageUseCase,
    private val createdMessageV2UseCase: CreatedMessageV2UseCase,
    private val blockedUserPort: BlockedUserPort,
) {

    @EventListener
    fun welcomeMessage(event: WelcomeMessageEvent) {
        createMessageUseCase.welcomeMessage(event.signUpUser)
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun welcomeMessageV2(event: WelcomeMessageEvent) {
        createdMessageV2UseCase.welcomeMessage(event.signUpUser)
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handleMessageBlockedEvent(event: MessageV2BlockedEvent) {
        val message = event.message
        if (message.isAnonymousRoom()) {
            return
        }

        val sender = event.sender

        if (message.isBlockedBy(viewer = sender)) {
            val blockedUser = BlockedUser.create(
                blockerId = sender.id,
                blockedId = event.receiver.id,
                messageId = message.id,
                isAlreadyBlocked = blockedUserPort.existsByBlockerIdAndBlockedIdAndMessageId(
                    blockerId = sender.id,
                    blockedId = event.receiver.id,
                    messageId = message.id
                )
            )
            blockedUserPort.save(blockedUser)
            return
        }

        blockedUserPort.deleteByBlockerIdAndBlockedIdAndMessageId(
            blockerId = sender.id,
            blockedId = event.receiver.id,
            messageId = message.id
        )
    }


}
