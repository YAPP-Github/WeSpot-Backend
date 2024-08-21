package com.wespot.user.service

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.port.out.UserPort
import org.springframework.http.HttpStatus

object UserFinder {

    fun findUserById(id: Long, userPort: UserPort) = userPort.findById(id)
        ?: throw CustomException(HttpStatus.NOT_FOUND, ExceptionView.TOAST, "유저를 찾을 수 없습니다.")

    fun findBlockedUserById(id: Long, userPort: UserPort) = userPort.findById(id)
        ?: throw CustomException(HttpStatus.NOT_FOUND, ExceptionView.TOAST, "차단된 유저를 찾을 수 없습니다.")

}
