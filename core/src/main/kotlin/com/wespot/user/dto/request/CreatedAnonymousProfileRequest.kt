package com.wespot.user.dto.request

data class CreatedAnonymousProfileRequest(
    val imageUrl: String,
    val name: String,
    val receiverId: Long,
) {
}
