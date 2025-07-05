package com.wespot.post

import com.wespot.common.BaseEntity
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "post_like")
class PostLikeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @field:NotNull
    val postId: Long,
    @field:NotNull
    val userId: Long,
    @Embedded
    val baseEntity: BaseEntity
) {
}
