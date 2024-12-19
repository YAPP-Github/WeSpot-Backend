package com.wespot.user.port.out

import com.wespot.user.UserVersion

interface UserVersionPort {

    fun save(userVersion: UserVersion): UserVersion

    fun findByUserId(id: Long): UserVersion?

    fun findAll(): List<UserVersion>

}
