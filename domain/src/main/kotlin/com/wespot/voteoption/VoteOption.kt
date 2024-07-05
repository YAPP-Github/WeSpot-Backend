package com.wespot.voteoption

import java.time.LocalDateTime

data class VoteOption(
    val id: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
}