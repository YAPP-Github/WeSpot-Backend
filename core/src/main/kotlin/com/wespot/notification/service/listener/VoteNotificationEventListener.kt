package com.wespot.notification.service.listener

import com.wespot.notification.port.`in`.VoteNotificationUseCase
import com.wespot.user.event.SignUpUserEvent
import com.wespot.vote.event.EndVoteEvent
import com.wespot.vote.event.ReceivedVoteEvent
import com.wespot.vote.event.RegisteredVoteEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class VoteNotificationEventListener(
    private val voteNotificationUseCase: VoteNotificationUseCase
) {

    @EventListener
    fun signUpNewUser(signUpUserEvent: SignUpUserEvent) {
        voteNotificationUseCase.signUpUser(signUpUserEvent.user)
    }

    @EventListener
    fun registerVote(registerVoteEvent: RegisteredVoteEvent) {
        voteNotificationUseCase.registerVote(registerVoteEvent.sender, registerVoteEvent.vote)
    }

    @EventListener
    fun receiveVote(receivedVoteEvent: ReceivedVoteEvent) {
        voteNotificationUseCase.receiveVote(receivedVoteEvent.receiver)
    }

    @EventListener
    fun endVote(endVoteEvent: EndVoteEvent) {
        voteNotificationUseCase.endVote()
    }

}
