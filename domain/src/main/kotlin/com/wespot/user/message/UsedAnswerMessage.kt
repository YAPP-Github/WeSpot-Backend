package com.wespot.user.message

import java.time.LocalDateTime

class UsedAnswerMessage(
    val id: Long,
    val userId: Long,
    val isUsedAnswerMessageFeature: Boolean = false,
    val createdAt: LocalDateTime,
) {

    companion object {
        fun create(
            userId: Long,
        ): UsedAnswerMessage {
            return UsedAnswerMessage(
                id = 0L,
                userId = userId,
                isUsedAnswerMessageFeature = true,
                createdAt = LocalDateTime.now(),
            )
        }
    }
}
