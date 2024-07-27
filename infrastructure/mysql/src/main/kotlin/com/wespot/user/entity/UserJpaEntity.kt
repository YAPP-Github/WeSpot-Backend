package com.wespot.user.entity

import com.wespot.common.BaseEntity
import com.wespot.user.RestrictionType
import com.wespot.user.Role
import jakarta.persistence.CascadeType
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull
import java.time.LocalDate
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

    @Enumerated(EnumType.STRING)
    @field: NotNull
    val restrictionType: RestrictionType,

    @field: NotNull
    val releaseDate: LocalDate,

    val withdrawAt: LocalDateTime?,

    @Embedded
    val baseEntity: BaseEntity,

    ) {

}
