package com.wespot.report

import com.wespot.common.BaseEntity
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
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
    val reportType: ReportType,

    @field:NotNull
    val targetId: Long,

    @field:NotNull
    val senderId: Long,

    @field:NotNull
    val receiverId: Long,

    @Embedded
    val baseEntity: BaseEntity

)
