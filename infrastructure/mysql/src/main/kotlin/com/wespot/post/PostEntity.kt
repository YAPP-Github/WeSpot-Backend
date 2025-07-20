package com.wespot.post

import com.wespot.common.BaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "post")
class PostEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val categoryId: Long,

    @field: NotNull
    val userId: Long,

    val title: String?,

    @field: NotNull
    val description: String,

    @field: NotNull
    val likeCount: Long,

    @field: NotNull
    val commentCount: Long,

    @field: NotNull
    val bookmarkCount: Long,

    @Embedded
    val baseEntity: BaseEntity
) {
}
