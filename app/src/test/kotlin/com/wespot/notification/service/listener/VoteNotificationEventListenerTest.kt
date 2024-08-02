package com.wespot.notification.service.listener

import com.wespot.notification.port.`in`.VoteNotificationUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class VoteNotificationEventListenerTest @Autowired constructor(
    private val voteNotificationUseCase: VoteNotificationUseCase
) {

//    @EventListener
//    fun signUpNewUser(signUpUserEvent: SignUpUserEvent) {
//        voteNotificationUseCase.signUpUser(signUpUserEvent.user)
//    }
//
//    @EventListener
//    fun registerVote(registerVoteEvent: RegisteredVoteEvent) {
//        voteNotificationUseCase.registerVote(registerVoteEvent.sender, registerVoteEvent.vote)
//    }
//
//    @EventListener
//    fun receiveVote(receivedVoteEvent: ReceivedVoteEvent) {
//        voteNotificationUseCase.receiveVote(receivedVoteEvent.receiver)
//    }
//
//    @EventListener
//    fun endVote(endVoteEvent: EndVoteEvent) {
//        voteNotificationUseCase.endVote()
//    }

}
