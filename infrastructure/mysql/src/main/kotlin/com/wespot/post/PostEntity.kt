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
    val category_id: Long,

    @field: NotNull
    val title: String,

    @field: NotNull
    val description: String,

    @field: NotNull
    val likeCount: String,

    @field: NotNull
    val commentCount: String,

    @Embedded
    val baseEntity: BaseEntity
) {
}
