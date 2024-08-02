package com.wespot.vote.service.listener

import com.wespot.user.event.SignUpUserEvent
import com.wespot.vote.port.`in`.CreatedVoteUseCase
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class VoteEventListener(
    private val createdVoteUseCase: CreatedVoteUseCase
) {

    @EventListener
    fun signUpNewUser(event: SignUpUserEvent) {
        createdVoteUseCase.createVoteByUser(event.user)
    }

}
