package com.wespot.user.service.listener

import com.wespot.user.User
import com.wespot.user.message.UsedAnswerMessage
import com.wespot.user.port.out.UsedAnswerMessagePort
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class UsedAnswerMessageEventListener(
    private val usedAnswerMessagePort: UsedAnswerMessagePort,
) {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun usedAnswerMessage(user: User) {
        if (usedAnswerMessagePort.existsByUserId(userId = user.id)) {
            return
        }

        val usedAnswerMessage = UsedAnswerMessage.create(userId = user.id)
        usedAnswerMessagePort.save(usedAnswerMessage = usedAnswerMessage)
    }

}
