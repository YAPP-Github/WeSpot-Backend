package com.wespot.user

import com.wespot.common.BaseEntity
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
    val schoolId: Long,

    @field: NotNull
    val grade: Int,

    @field: NotNull
    val groupNumber: Int,

    @Embedded
    @field: NotNull
    val setting: SettingJpaEntity,

    @Embedded
    val social: SocialJpaEntity,

    @Enumerated(EnumType.STRING)
    val role: Role,

    val withdrawAt: LocalDateTime?,

    @Embedded
    @field: NotNull
    val baseEntity: BaseEntity,


) {

}
