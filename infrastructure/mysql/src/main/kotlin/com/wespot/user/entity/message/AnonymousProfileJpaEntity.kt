package com.wespot.user.entity.message

import com.wespot.common.BaseEntity
import jakarta.persistence.*
import software.amazon.awssdk.annotations.NotNull

@Entity
@Table(
    name = "anonymous_profile",
    uniqueConstraints = [UniqueConstraint(columnNames = ["name"])]
)
class AnonymousProfileJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val imageUrl: String,

    @field: NotNull
    @Column(unique = true)
    val name: String,

    @field: NotNull
    val ownerId: Long,

    @field: NotNull
    val receiverId: Long,

    @Embedded
    val baseEntity: BaseEntity

)
