package com.wespot.user

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
    val profile: Profile,
    val fcm: FCM,
    val social: Social,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val withdrawAt: LocalDateTime,
) {
}
