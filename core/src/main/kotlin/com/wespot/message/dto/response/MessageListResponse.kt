package com.wespot.message.dto.response

data class MessageListResponse(
    val messages: List<MessageResponse>,
    val hasNext: Boolean
) {
    companion object {

        fun from(messages: List<MessageResponse>, hasNext: Boolean): MessageListResponse {
            return MessageListResponse(
                messages = messages,
                hasNext = hasNext
            )
        }

    }
}

