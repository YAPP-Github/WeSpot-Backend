package com.wespot.post

import jakarta.persistence.Embedded
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

class PostReport(
    val id: Long,
    val postId: Long,
    val userId: Long,
    val createdAt: LocalDateTime
) {
}
