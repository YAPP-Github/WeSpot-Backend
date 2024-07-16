package com.wespot.school

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "school")
class SchoolJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

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
