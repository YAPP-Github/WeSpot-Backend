package com.wespot.comment

import com.wespot.common.BaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "comment_like")
class CommentLikeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val commentId: Long,

    @field: NotNull
    val userId: Long,

    @Embedded
    val baseEntity: BaseEntity
) {
}
