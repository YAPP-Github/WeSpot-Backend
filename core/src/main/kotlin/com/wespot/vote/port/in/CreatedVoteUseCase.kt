package com.wespot.vote.port.`in`

import com.wespot.user.User

interface CreatedVoteUseCase {

    fun createVotes()

    fun createVoteByUser(user: User)

}
