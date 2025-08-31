package com.wespot.post

import com.wespot.common.BaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull


@Entity
@Table(name = "post_report_reason")
data class PostReportReasonEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val postReportId: Long,

    @field: NotNull
    val reportReasonId: Long,

    val customReason: String? = null,

    @Embedded
    val baseEntity: BaseEntity
) {

    fun registeredInPostReport(postReportId: Long): PostReportReasonEntity {
        return copy(postReportId = postReportId)
    }

}
