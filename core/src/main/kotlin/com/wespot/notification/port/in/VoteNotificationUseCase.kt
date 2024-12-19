package com.wespot.notification.port.`in`

import com.wespot.user.User
import com.wespot.vote.Vote

interface VoteNotificationUseCase {

    fun encourageVote()

    fun signUpUser(user: User)

    fun registerVote(numberOfSenderBeforeVote: Int, sender: User, vote: Vote)

    fun endVote()

    fun receiveVote(sender: User, receiver: User)

}
