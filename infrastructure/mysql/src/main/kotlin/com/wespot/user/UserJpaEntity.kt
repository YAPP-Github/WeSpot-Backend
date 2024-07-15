package com.wespot.user

import com.wespot.common.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class UserJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @field: NotNull
    val name: String,

    @field: NotNull
    val schoolId: Long,

    @field: NotNull
    val grade: Int,

    @field: NotNull
    val groupNumber: Int,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JoinColumn(
        name = "setting_id", foreignKey = ForeignKey(name = "fk_users_setting_id")
    )
    @field: NotNull
    val setting: SettingJpaEntity,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JoinColumn(
        name = "profile_id", foreignKey = ForeignKey(name = "fk_users_profile_id")
    )
    @field: NotNull
    val profile: ProfileJpaEntity,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JoinColumn(
        name = "fcm_id", foreignKey = ForeignKey(name = "fk_users_fcm_id")
    )
    val fcm: FCMJpaEntity,

    @Embedded
    val social: SocialJpaEntity,

    @Embedded
    @field: NotNull
    val baseEntity: BaseEntity,

    val withdrawAt: LocalDateTime

) {

}
