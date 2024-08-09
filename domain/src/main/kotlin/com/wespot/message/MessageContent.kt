package com.wespot.message

import com.wespot.common.ProfanityChecker

data class MessageContent(
    val content: String
) {

    companion object {
        fun from(content: String): MessageContent {
            validateContent(content)
            return MessageContent(content)
        }

        private fun validateContent(content: String) {
            require(!ProfanityChecker.checkProfanity(content)) { "메시지의 내용에는 욕설이 포함될 수 없습니다." }
            require(content.isNotBlank()) { "메시지의 내용은 필수로 존재해야합니다." }
        }
    }

}
