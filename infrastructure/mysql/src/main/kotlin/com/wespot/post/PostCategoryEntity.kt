package com.wespot.post

import com.wespot.common.BaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "post_category")
class PostCategoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val majorCategoryName: String,

    @field:NotNull
    val name: String,

    @Embedded
    val baseEntity: BaseEntity
) {
}
