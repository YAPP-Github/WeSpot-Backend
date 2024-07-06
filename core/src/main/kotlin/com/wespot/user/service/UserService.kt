package com.wespot.user.service

import com.wespot.user.port.`in`.UserUseCase
import com.wespot.user.port.out.UserStatePort

class UserService(
    private val userStatePort: UserStatePort
) : UserUseCase {
}