package com.wespot.user.dto.response

data class BlockedUserResponse(
    val id: Long,
){
    companion object {
        fun of(id: Long): BlockedUserResponse {
            return BlockedUserResponse(
                id = id,
            )
        }
    }
}
