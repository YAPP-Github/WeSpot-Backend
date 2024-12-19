package com.wespot.voteoption

import java.time.LocalDateTime

data class VoteOption(
    val id: Long,
    val content: VoteOptionContent,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {

    companion object {

        fun createInitialState(
            content: String
        ): VoteOption {
            return VoteOption(
                id = 0L,
                content = VoteOptionContent.from(content),
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        }

        fun of(
            id: Long,
            content: String,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime
        ): VoteOption {
            return VoteOption(
                id = id,
                content = VoteOptionContent.from(content),
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }

    }

    fun update(
        content: String,
    ): VoteOption {
        return VoteOption(
            id = id,
            content = VoteOptionContent.from(content),
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

}
