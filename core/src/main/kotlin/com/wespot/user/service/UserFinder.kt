package com.wespot.user.service

import com.wespot.user.port.out.UserPort

object UserFinder {

    fun findUserById(id: Long, userPort: UserPort) = userPort.findById(id)
        ?: throw NoSuchElementException("유저를 찾을 수 없습니다.")

    fun findBlockedUserById(id: Long, userPort: UserPort) = userPort.findById(id)
        ?: throw NoSuchElementException("차단된 유저를 찾을 수 없습니다.")

}
