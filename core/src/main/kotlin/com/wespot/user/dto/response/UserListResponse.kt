package com.wespot.user.dto.response

data class UserListResponse(
    val users: List<UserResponse>,
    val hasNext: Boolean,
    val lastCursorId: Long,
) {
    companion object {
        fun from(users: List<UserResponse>, hasNext: Boolean): UserListResponse {
            return UserListResponse(
                users = users,
                hasNext = hasNext,
                lastCursorId = users.lastOrNull()?.id ?: 0L
            )
        }
    }
}
