package com.wespot.user

import com.wespot.school.School
import java.time.LocalDateTime

data class User(
    val id: Long,
    val school: School,
    val grade: Int,
    val group: Int,
    val setting: Setting,
    val profile: Profile,
    val fcm: FCM,
    val social: Social,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val withdrawAt: LocalDateTime,
) {
}