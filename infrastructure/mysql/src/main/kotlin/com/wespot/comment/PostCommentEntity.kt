package com.wespot.comment

import com.wespot.common.BaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "post_comment")
class PostCommentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val postId: Long,

    @field: NotNull
    val userId: Long,

    @field: NotNull
    val content: String,

    @field: NotNull
    val likeCount: Long,

    @field: NotNull
    val reportCount: Long,

    @Embedded
    val baseEntity: BaseEntity
) {
}
