package com.wespot.message.dto.response

data class SendMessageStatusResponse(
    val isSendAllowed: Boolean,
    val remainingMessages: Int
){
    companion object {

        fun from(
            isSendAllowed: Boolean,
            remainingMessages: Int
        ): SendMessageStatusResponse {
            return SendMessageStatusResponse(
                isSendAllowed = isSendAllowed,
                remainingMessages = remainingMessages
            )
        }
    }

}
