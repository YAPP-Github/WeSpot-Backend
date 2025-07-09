package com.wespot.comment

import com.wespot.common.BaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "post_comment_report")
class PostCommentReportEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val postCommentId: Long,

    @field: NotNull
    val userId: Long,

    @Embedded
    val baseEntity: BaseEntity
) {
}
