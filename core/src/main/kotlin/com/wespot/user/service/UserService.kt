package com.wespot.user.service

import com.wespot.user.port.`in`.UserUseCase
import com.wespot.user.port.out.UserPort

class UserService(
    private val userPort: UserPort
) : UserUseCase {
}
