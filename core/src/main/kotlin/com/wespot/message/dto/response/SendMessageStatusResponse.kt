package com.wespot.message.dto.response

data class SendMessageStatusResponse(
    val isSendAllowed: Boolean,
    val remainingMessages: Int,
    val unReadMessages: Int
){
    companion object {

        fun from(
            isSendAllowed: Boolean,
            remainingMessages: Int,
            unReadMessages: Int
        ): SendMessageStatusResponse {
            return SendMessageStatusResponse(
                isSendAllowed = isSendAllowed,
                remainingMessages = remainingMessages,
                unReadMessages = unReadMessages
            )
        }
    }

}
