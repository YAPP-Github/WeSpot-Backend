package com.wespot.user.entity

import com.wespot.user.ConsentType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_consent")
class UserConsentJpaEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", foreignKey = ForeignKey(name = "fk_user_consent_users_id"))
    val user: UserJpaEntity,

    @Enumerated(EnumType.STRING)
    val consentType: ConsentType?,

    val consentValue : Boolean?,

    val consentedAt: LocalDateTime?
)