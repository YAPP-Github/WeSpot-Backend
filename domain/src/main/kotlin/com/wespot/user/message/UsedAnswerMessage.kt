package com.wespot.user.message

import java.time.LocalDateTime

class UsedAnswerMessage(
    val id: Long,
    val userId: Long,
    val isUsedAnswerMessageFeature: Boolean = false,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
}
