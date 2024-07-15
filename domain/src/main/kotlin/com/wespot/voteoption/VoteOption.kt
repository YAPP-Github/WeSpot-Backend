package com.wespot.voteoption

import java.time.LocalDateTime
import java.util.*

data class VoteOption(
    val id: Long?,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
) {

    companion object {
        fun of(
            id: Long?,
            content: String,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime?
        ): VoteOption {
            if (Objects.isNull(content) || content.isBlank()) {
                throw IllegalArgumentException("선택지의 내용은 필수로 존재해야합니다.")
            }
            return VoteOption(
                id = id,
                content = content,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }

}