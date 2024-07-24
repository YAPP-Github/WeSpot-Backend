package com.wespot.user.dto.request

data class SearchUserRequest(
    val keyword: String,
    val cursorId: Long
)
