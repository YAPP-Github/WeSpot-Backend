package com.wespot.message.service.listener

import com.wespot.message.port.`in`.CreateMessageUseCase
import com.wespot.user.User
import com.wespot.user.event.WelcomeMessageEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class MessageEventListener(
    private val createMessageUseCase: CreateMessageUseCase
) {

    @EventListener
    fun welcomeMessage(event: WelcomeMessageEvent) {
        createMessageUseCase.welcomeMessage(event.signUpUser)
    }

}
