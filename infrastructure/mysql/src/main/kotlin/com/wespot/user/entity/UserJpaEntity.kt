package com.wespot.user.entity

import com.wespot.common.BaseEntity
import com.wespot.user.Role
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class UserJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val email: String,

    @field: NotNull
    val password: String,

    @field: NotNull
    val name: String,

    @field: NotNull
    val introduction: String,

    @field: NotNull
    val gender: String,

    @field: NotNull
    val schoolId: Long,

    @field: NotNull
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "profile_id", foreignKey = ForeignKey(name = "fk_users_profile_id"))
    val profile: ProfileJpaEntity,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "fcm_id", foreignKey = ForeignKey(name = "fk_users_fcm_id"))
    val fcm: FCMJpaEntity,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "user_consent_id", foreignKey = ForeignKey(name = "fk_users_user_consent_id"))
    val userConsent: UserConsentJpaEntity,

    @field: NotNull
    val grade: Int,

    @field: NotNull
    val classNumber: Int,

    @Embedded
    @field: NotNull
    val setting: SettingJpaEntity,

    @Embedded
    val social: SocialJpaEntity,

    @Enumerated(EnumType.STRING)
    val role: Role,

    val withdrawAt: LocalDateTime?,

    @Embedded
    val baseEntity: BaseEntity,

    ) {

}
