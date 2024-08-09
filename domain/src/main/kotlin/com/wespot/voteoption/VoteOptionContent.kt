package com.wespot.voteoption

import com.wespot.common.ProfanityChecker

data class VoteOptionContent(
    val content: String
) {

    companion object {

        fun from(content: String): VoteOptionContent {
            ProfanityChecker.checkProfanity(content)
            validateContent(content)
            return VoteOptionContent(content)
        }

        private fun validateContent(content: String) {
            require(!ProfanityChecker.checkProfanity(content)) { "선택지의 내용에는 욕설이 포함될 수 없습니다." }
            require(content.isNotBlank()) { "선택지의 내용은 필수로 존재해야합니다." }
        }

    }
}
