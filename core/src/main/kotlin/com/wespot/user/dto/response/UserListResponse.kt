package com.wespot.user.dto.response

data class UserListResponse(
    val users: List<UserResponse>,
    val hasNext: Boolean
) {
    companion object {
        fun from(users: List<UserResponse>, hasNext: Boolean): UserListResponse {
            return UserListResponse(
                users = users,
                hasNext = hasNext
            )
        }
    }
}
