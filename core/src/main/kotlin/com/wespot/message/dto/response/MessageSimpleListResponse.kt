package com.wespot.message.dto.response

data class MessageSimpleListResponse(
    val messages: List<MessageResponse>,
) {
    companion object {

        fun from(messages: List<MessageResponse>): MessageSimpleListResponse {
            return MessageSimpleListResponse(
                messages = messages
            )
        }

    }
}

