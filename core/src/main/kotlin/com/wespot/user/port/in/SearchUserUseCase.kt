package com.wespot.user.port.`in`

import com.wespot.user.dto.response.UserListResponse

interface SearchUserUseCase {

    fun searchUsers(
        keyword: String,
        cursorId: Long
    ): UserListResponse

}
