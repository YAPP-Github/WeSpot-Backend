package com.wespot.notification

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.User
import com.wespot.user.UserClass
import org.springframework.http.HttpStatus

object ClassmateValidator {

    fun validateContainNonClassmateUser(users: List<User>) {
        val groupBy = users.groupBy { UserClass.of(it) }
        require(groupBy.size == 1) {
            throw CustomException(
                HttpStatus.BAD_REQUEST,
                ExceptionView.TOAST,
                "다른 학급의 사용자가 포함되어 있습니다."
            )
        }
    }

}
