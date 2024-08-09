package com.wespot.user

import com.wespot.common.BadWordsChecker

data class Introduction(
    val introduction: String
) {
    companion object {

        fun emptyIntroduction(): Introduction {
            return Introduction("")
        }

        fun from(introduction: String): Introduction {
            BadWordsChecker.checkProfanity(introduction)
            return Introduction(introduction)
        }

        private fun validateIntroduction(content: String) {
            require(!BadWordsChecker.checkProfanity(content)) { "소개에는 욕설이 포함될 수 없습니다." }
        }

    }
}
