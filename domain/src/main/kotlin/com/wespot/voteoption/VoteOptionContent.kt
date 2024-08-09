package com.wespot.voteoption

import com.wespot.common.BadWordsChecker

data class VoteOptionContent(
    val content: String
) {

    companion object {

        fun from(content: String): VoteOptionContent {
            BadWordsChecker.check(content)
            validateContent(content)
            return VoteOptionContent(content)
        }

        private fun validateContent(content: String) {
            require(content.isNotBlank()) { "선택지의 내용은 필수로 존재해야합니다." }
        }

    }
}
