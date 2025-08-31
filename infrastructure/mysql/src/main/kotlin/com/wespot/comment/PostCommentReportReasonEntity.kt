package com.wespot.comment

import com.wespot.common.BaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "post_comment_report_reason")
data class PostCommentReportReasonEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val postCommentReportId: Long,

    @field: NotNull
    val reportReasonId: Long,

    val customReason: String? = null,

    @Embedded
    val baseEntity: BaseEntity
) {
}
