package com.wespot.school

import com.wespot.user.entity.UserJpaEntity
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "school")
class SchoolJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", foreignKey = ForeignKey(name = "fk_school_users_id"))
    val user: UserJpaEntity,

    @field:NotNull
    val name: String,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    val schoolType: SchoolType,

    @field:NotNull
    val region: String,

    @field:NotNull
    val address: String

)
