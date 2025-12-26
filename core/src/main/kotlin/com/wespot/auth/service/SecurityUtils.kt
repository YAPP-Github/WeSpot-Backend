package com.wespot.auth.service


import com.wespot.auth.PrincipalDetails
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder

object SecurityUtils {
    fun getLoginUserId(userPort: UserPort): Long {
        val principal = SecurityContextHolder.getContext().authentication.principal as PrincipalDetails
        return userPort.findByEmail(principal.username)?.id
            ?: throw CustomException(HttpStatus.NOT_FOUND, ExceptionView.TOAST, "해당 계정이 존재하지 않습니다.")
    }

    fun getLoginUser(userPort: UserPort): User {
        val principal = SecurityContextHolder.getContext().authentication.principal as PrincipalDetails

        return userPort.findByEmail(principal.username)
            ?: throw CustomException(HttpStatus.NOT_FOUND, ExceptionView.TOAST, "해당 계정이 존재하지 않습니다.")
//        return userPort.findByName("김재연")
//            ?: throw CustomException(HttpStatus.NOT_FOUND, ExceptionView.TOAST, "해당 계정이 존재하지 않습니다.")
    }
}
