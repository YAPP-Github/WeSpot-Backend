package com.wespot.message.dto.response

data class MessageListResponse(
    val messages: List<MessageResponse>
){
    companion object {

        fun from(messages: List<MessageResponse>): MessageListResponse {
            return MessageListResponse(
                messages = messages
            )
        }

    }
}

