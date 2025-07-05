package com.wespot.post

import com.wespot.common.BaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "post_report")
class PostReportEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val postId: Long,

    @field: NotNull
    val userId: Long,

    @Embedded
    val baseEntity: BaseEntity
) {
}
