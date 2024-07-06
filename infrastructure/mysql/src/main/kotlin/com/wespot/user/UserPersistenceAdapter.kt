package com.wespot.user

import com.wespot.user.port.out.UserStatePort
import org.springframework.stereotype.Repository

@Repository
class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository
) : UserStatePort {
}