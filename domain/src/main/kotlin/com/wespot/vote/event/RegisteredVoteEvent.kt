package com.wespot.vote.event

import com.wespot.user.User
import com.wespot.vote.Vote

data class RegisteredVoteEvent(
    val sender: User,
    val vote: Vote,
)
