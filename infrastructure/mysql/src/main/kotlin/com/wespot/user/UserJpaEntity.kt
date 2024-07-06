package com.wespot.user

import com.wespot.common.BaseEntity
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "user")
class UserJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val schoolId: Long,

    @field: NotNull
    val grade: Int,

    @field: NotNull
    val group: Int,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "setting_id", foreignKey = ForeignKey(name = "fk_user_setting_id")
    )
    @field: NotNull
    val setting: SettingJpaEntity,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "profile_id", foreignKey = ForeignKey(name = "fk_user_profile_id")
    )
    @field: NotNull
    val profile: ProfileJpaEntity,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "fcm_id", foreignKey = ForeignKey(name = "fk_user_fcm_id")
    )
    val fcm: FCMJpaEntity,

    @Embedded
    @field: NotNull
    val social: SocialJpaEntity,

    @Embedded
    @field: NotNull
    val baseEntity: BaseEntity,

    val withdrawAt: LocalDateTime

) {

}
