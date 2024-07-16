package com.wespot.user

import com.wespot.school.School
import java.time.LocalDateTime

data class User(
    val id: Long,
    val email: String,
    val password: String,
    val name: String,
    val introduction: String,
    val role: Role,
    val schoolId: Long,
    val grade: Int,
    val groupNumber: Int,
    val setting: Setting,
    val social: Social,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val withdrawAt: LocalDateTime?,
) {

    fun withdraw(user: User) =
        User(
            id = id,
            email = email,
            password = password,
            role = Role.GUEST,
            grade = grade,
            groupNumber = groupNumber,
            setting = setting,
            social = social,
            createdAt = createdAt,
            updatedAt = LocalDateTime.now(),
            withdrawAt = LocalDateTime.now(),
        )

    companion object {
        fun create(
            email: String,
            password: String,
            grade: Int,
            groupNumber: Int,
            social: Social
        ) =
            User(
                id = 0,
                email = email,
                password = password,
                role = Role.USER,
                grade = grade,
                groupNumber = groupNumber,
                setting = Setting(),
                social = social,
            )
    }
}
