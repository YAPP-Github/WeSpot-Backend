package com.wespot.report

import org.springframework.data.jpa.repository.JpaRepository

interface ReportJpaRepository : JpaRepository<ReportJpaEntity, Long> {

    fun findAllByReceiverIdAndReportType(reportedId: Long, reportType: ReportType): List<ReportJpaEntity>

    fun deleteByReportTypeAndTargetIdAndReceiverId(reportType: ReportType, targetId: Long, receiverId: Long)

    fun deleteBySenderIdOrReceiverId(senderId: Long, receiverId: Long)

}
