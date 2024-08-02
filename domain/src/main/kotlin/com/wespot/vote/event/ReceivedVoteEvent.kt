package com.wespot.vote.event

import com.wespot.user.User

data class ReceivedVoteEvent(
    val receiver: User
)
