package com.wespot.post

import com.wespot.common.BaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "post_notification")
class PostNotificationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val userId: Long,

    @field: NotNull
    val postId: Long,

    val baseEntity: BaseEntity
) {

}
