package com.wespot.user.message

import java.time.LocalDateTime

class UsedAnswerMessage(
    val id: Long = 0L,
    val userId: Long,
    val isUsedAnswerMessageFeature: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
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
