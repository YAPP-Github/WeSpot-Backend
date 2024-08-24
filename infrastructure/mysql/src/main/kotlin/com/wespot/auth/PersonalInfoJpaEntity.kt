package com.wespot.auth

import com.wespot.user.RestrictionType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "personal_info")
class PersonalInfoJpaEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val email: String,

    val name: String,

    val socialId: String,

    val socialEmail: String?,

    val socialRefreshToken: String?,

    val restriction: Long,

    val storedAt: LocalDateTime
){

}
