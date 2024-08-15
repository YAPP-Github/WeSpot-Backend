package com.wespot.message.dto.response

data class MessageBlockedListResponse(
    val messages: List<MessageBlockedResponse>,
    val hasNext: Boolean,
    val lastCursorId: Long,
) {
    companion object {

        fun from(messages: List<MessageBlockedResponse>, hasNext: Boolean): MessageBlockedListResponse {
            return MessageBlockedListResponse(
                messages = messages,
                hasNext = hasNext,
                lastCursorId = messages.lastOrNull()?.id ?: 0L
            )
        }

    }
}

