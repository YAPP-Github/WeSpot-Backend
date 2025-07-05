package com.wespot.post

import com.wespot.common.BaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "post_profile")
class PostProfileEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val userId: Long,

    @field: NotNull
    val url: Long,

    @field: NotNull
    val name: String,

    @Embedded
    val baseEntity: BaseEntity
) {
}
