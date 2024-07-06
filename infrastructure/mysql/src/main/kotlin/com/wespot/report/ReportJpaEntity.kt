package com.wespot.report

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "report")
class ReportJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:NotNull
    val id: Long,

    @Enumerated(value = EnumType.STRING)
    @field:NotNull
    val type: ReportType,

    @field:NotNull
    val reporterId: Long,

    @field:NotNull
    val reportedId: Long,

    @field:NotNull
    val isApprove: Boolean

)
