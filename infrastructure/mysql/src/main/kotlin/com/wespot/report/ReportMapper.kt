package com.wespot.report

import com.wespot.common.BaseEntity

object ReportMapper {

    fun toDomainEntity(reportJpaEntity: ReportJpaEntity) = Report(
        id = reportJpaEntity.id,
        reportType = reportJpaEntity.reportType,
        targetId = reportJpaEntity.targetId,
        senderId = reportJpaEntity.senderId,
        receiverId = reportJpaEntity.receiverId,
        createdAt = reportJpaEntity.baseEntity.createdAt
    )

    fun toJpaEntity(report: Report) = ReportJpaEntity(
        id = report.id,
        reportType = report.reportType,
        targetId = report.targetId,
        senderId = report.senderId,
        receiverId = report.receiverId,
        baseEntity = BaseEntity(report.createdAt, report.createdAt)
    )

}
