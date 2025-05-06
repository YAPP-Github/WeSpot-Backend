package com.wespot.message.service.listener

import com.wespot.message.port.`in`.CreateMessageUseCase
import com.wespot.message.port.`in`.CreatedMessageV2UseCase
import com.wespot.user.event.WelcomeMessageEvent
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
    private val createdMessageV2UseCase: CreatedMessageV2UseCase
) {

    @EventListener
    fun welcomeMessage(event: WelcomeMessageEvent) {
        createMessageUseCase.welcomeMessage(event.signUpUser)
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun welcomeMessageV2(event: WelcomeMessageEvent){
        createdMessageV2UseCase.welcomeMessage(event.signUpUser)
    }


}
