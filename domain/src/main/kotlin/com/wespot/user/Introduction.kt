package com.wespot.user

import com.wespot.common.BadWordsChecker

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
            require(!BadWordsChecker.checkProfanity(content)) { "소개에는 욕설이 포함될 수 없습니다." }
        }

    }
}
