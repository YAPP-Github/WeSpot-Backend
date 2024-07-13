package com.wespot.user

import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Repository

@Repository
class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository
) : UserPort {
}