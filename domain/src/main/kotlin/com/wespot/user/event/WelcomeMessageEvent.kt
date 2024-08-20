package com.wespot.user.event

import com.wespot.user.User

data class WelcomeMessageEvent(
    val signUpUser: User
)
