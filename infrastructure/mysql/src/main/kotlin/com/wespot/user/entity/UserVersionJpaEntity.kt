package com.wespot.user.entity

import com.wespot.common.BaseEntity
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import software.amazon.awssdk.annotations.NotNull

@Entity
@Table(name = "users_app_version")
class UserVersionJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val userId: Long,

    val iosVersionName: String,

    val androidVersionName: String,

    val iosVersionNameWhenSignUp: String,

    val androidVersionNameWhenSignUp: String,

    @Embedded
    val baseEntity: BaseEntity
) {


}
