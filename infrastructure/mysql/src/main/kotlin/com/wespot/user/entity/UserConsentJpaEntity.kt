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

    @Enumerated(EnumType.STRING)
    val consentType: ConsentType?,

    val consentValue : Boolean?,

    val consentedAt: LocalDateTime?
)