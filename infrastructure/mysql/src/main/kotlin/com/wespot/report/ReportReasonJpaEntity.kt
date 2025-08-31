package com.wespot.report

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "report_reason")
data class ReportReasonJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:NotNull
    val id: Long = 0L,

    @field:NotNull
    val content: String,

    val canReceiveReason: Boolean

) {
}
