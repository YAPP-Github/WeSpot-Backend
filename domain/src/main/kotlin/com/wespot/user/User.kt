package com.wespot.user

import java.time.LocalDateTime

data class User(
    val id: Long?,
    val name: String,
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