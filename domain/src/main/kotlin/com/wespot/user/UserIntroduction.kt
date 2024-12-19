package com.wespot.user

import com.wespot.common.ProfanityChecker
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import org.springframework.http.HttpStatus

data class UserIntroduction(
    val introduction: String
) {
    companion object {

        fun emptyUserIntroduction(): UserIntroduction {
            return UserIntroduction("")
        }

        fun from(introduction: String): UserIntroduction {
            validateUserIntroduction(introduction)

            return UserIntroduction(introduction)
        }

        fun fromNullable(introduction: String?): UserIntroduction {
            val notNullIntroduction = introduction ?: ""
            validateUserIntroduction(notNullIntroduction)

            return UserIntroduction(notNullIntroduction)
        }

        private fun validateUserIntroduction(content: String) {
            require(!ProfanityChecker.checkProfanity(content)) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST,
                    ExceptionView.TOAST,
                    "소개에 비속어가 포함되어 있습니다."
                )
            }
        }

    }
}
