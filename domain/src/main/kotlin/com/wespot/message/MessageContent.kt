package com.wespot.message

import com.wespot.common.ProfanityChecker
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import org.springframework.http.HttpStatus

data class MessageContent(
    val content: String
) {

    companion object {
        fun from(content: String): MessageContent {
            validateContent(content)
            return MessageContent(content)
        }

        private fun validateContent(content: String) {
            require(!ProfanityChecker.checkProfanity(content)) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST,
                    ExceptionView.TOAST,
                    "메시지의 내용에 비속어가 포함되어 있습니다."
                )
            }
            require(content.isNotBlank()) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST,
                    ExceptionView.TOAST,
                    "메시지의 내용은 필수로 존재해야합니다."
                )
            }
        }
    }

}
