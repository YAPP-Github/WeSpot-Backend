package com.wespot.vote.service

import com.wespot.user.event.VoteCreateEvent
import com.wespot.vote.port.`in`.CreatedVoteUseCase
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class VoteEventListener(
    private val createdVoteUseCase: CreatedVoteUseCase
) {

    @EventListener
    fun signUpNewUser(event: VoteCreateEvent) {
        createdVoteUseCase.createVoteByUser(event.user)
    }

}
