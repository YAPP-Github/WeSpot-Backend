package com.wespot.message.dto.response

data class UnreadMessageResponse(
    val unReadMessages: Int
){
    companion object {
        fun of(unReadMessages: Int): UnreadMessageResponse {
            return UnreadMessageResponse(
                unReadMessages = unReadMessages
            )
        }
    }
}
