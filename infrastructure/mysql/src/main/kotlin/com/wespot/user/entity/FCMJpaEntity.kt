package com.wespot.user.entity

import com.wespot.common.BaseEntity
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "fcm")
class FCMJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", foreignKey = ForeignKey(name = "fk_fcm_users_id"))
    val user: UserJpaEntity,

    @field: NotNull
    val fcmToken: String?,

    @Embedded
    @field: NotNull
    val baseEntity: BaseEntity?

)
