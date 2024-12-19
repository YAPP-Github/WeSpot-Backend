package com.wespot.vote.event

import com.wespot.user.User

data class ReceivedVoteEvent(
    val sender: User,
    val receiver: User
)
