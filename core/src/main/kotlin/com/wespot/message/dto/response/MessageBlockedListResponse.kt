package com.wespot.message.dto.response

data class MessageBlockedListResponse(
    val messages: List<MessageBlockedResponse>,
    val hasNext: Boolean
) {
    companion object {

        fun from(messages: List<MessageBlockedResponse>, hasNext: Boolean): MessageBlockedListResponse {
            return MessageBlockedListResponse(
                messages = messages,
                hasNext = hasNext
            )
        }

    }
}

