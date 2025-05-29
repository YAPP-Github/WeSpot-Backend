package com.wespot.message.dto.response

data class SendMessageStatusResponse(
    val isSendAllowed: Boolean,
    val countRemainingMessages: Int,
    val countUnReadMessages: Int
) {
    companion object {

        fun of(
            isSendAllowed: Boolean,
            countRemainingMessages: Int,
            countUnReadMessages: Int
        ): SendMessageStatusResponse {
            return SendMessageStatusResponse(
                isSendAllowed = isSendAllowed,
                countRemainingMessages = countRemainingMessages,
                countUnReadMessages = countUnReadMessages
            )
        }
    }

}
