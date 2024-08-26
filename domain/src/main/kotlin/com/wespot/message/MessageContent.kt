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

        fun createWelcomeMessage(receiverName: String): MessageContent {
            return MessageContent("안녕하세요 ${receiverName}님 \n" +
                "위스팟에 오신 것을 정말 환영해요!\n" +
                "\n" +
                "제가 큐피트가 되어 ${receiverName}님의 소중한 마음들을 전달해 드릴테니 언제든 익명 쪽지함을 찾아와 주세요!\n" +
                "\n" +
                "설레는 시간을 보내시길 바라며..")
        }
    }

}
