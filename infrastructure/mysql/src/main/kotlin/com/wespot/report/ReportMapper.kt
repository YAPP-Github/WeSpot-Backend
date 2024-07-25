package com.wespot.report

object ReportMapper {

    fun toDomainEntity(reportJpaEntity: ReportJpaEntity) = Report(
        id = reportJpaEntity.id,
        reportType = reportJpaEntity.reportType,
        targetId = reportJpaEntity.targetId,
        senderId = reportJpaEntity.senderId,
        receiverId = reportJpaEntity.receiverId
    )

    fun toJpaEntity(report: Report) = ReportJpaEntity(
        id = report.id,
        reportType = report.reportType,
        targetId = report.targetId,
        senderId = report.senderId,
        receiverId = report.receiverId
    )

}
