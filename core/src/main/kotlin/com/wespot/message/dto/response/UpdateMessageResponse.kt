package com.wespot.message.dto.response

data class UpdateMessageResponse(
    val id: Long
){
    companion object {

        fun from(id: Long): UpdateMessageResponse {
            return UpdateMessageResponse(
                id = id
            )
        }

    }
}
