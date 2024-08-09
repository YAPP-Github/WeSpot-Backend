package com.wespot.user.event

import com.wespot.user.User

data class CreatedVoteEvent(
    val signUpUser: User
)
