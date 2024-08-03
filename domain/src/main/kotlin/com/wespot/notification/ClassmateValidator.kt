package com.wespot.notification

import com.wespot.user.User
import com.wespot.user.UserClass

object ClassmateValidator {

    fun validateContainNonClassmateUser(users: List<User>) {
        val groupBy = users.groupBy { UserClass.of(it) }
        require(groupBy.size == 1) { "다른 학급의 사용자가 포함되어 있습니다." }
    }

}
