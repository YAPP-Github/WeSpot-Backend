package com.wespot.message.port.`in`

import com.wespot.user.User

interface CreateMessageUseCase {

    fun welcomeMessage(loginUser: User)

}
