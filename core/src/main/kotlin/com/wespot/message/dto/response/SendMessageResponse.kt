package com.wespot.message.dto.response

data class SendMessageResponse(
    val id: Long,
) {
    companion object {

        fun from(id: Long): SendMessageResponse {
            return SendMessageResponse(
                id = id
            )
        }

    }
}
