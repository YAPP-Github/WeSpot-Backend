package com.wespot.user

import com.wespot.common.ProfanityChecker

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

        private fun validateUserIntroduction(content: String) {
            require(!ProfanityChecker.checkProfanity(content)) { "소개에 비속어가 포함되어 있습니다." }
        }

    }
}
