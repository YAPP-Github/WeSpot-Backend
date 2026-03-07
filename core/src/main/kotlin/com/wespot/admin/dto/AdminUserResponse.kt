package com.wespot.admin.dto

import com.wespot.user.User

data class AdminUserResponse(
    val id: Long,
    val name: String,
    val email: String,
    val schoolName: String,
    val grade: Int,
    val classNumber: Int,
    val withdrawalStatus: String,
) {
    companion object {
        fun from(user: User): AdminUserResponse {
            return AdminUserResponse(
                id = user.id,
                name = user.name,
                email = user.email,
                schoolName = user.school.name,
                grade = user.grade,
                classNumber = user.classNumber,
                withdrawalStatus = user.withdrawalStatus.name,
            )
        }
    }
}
