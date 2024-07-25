package com.wespot.report

import com.wespot.report.port.out.ReportPort
import org.springframework.stereotype.Repository

@Repository
class ReportPersistenceAdapter(
    private val reportJpaRepository: ReportJpaRepository
) : ReportPort {

    override fun findAllByReportedIdAndReportType(reportedId: Long, reportType: ReportType): List<Report> {
        val findAllByReportedIdAndReportType =
            reportJpaRepository.findAllByReceiverIdAndReportType(reportedId, reportType)

        return findAllByReportedIdAndReportType.map { ReportMapper.toDomainEntity(it) }
    }

    override fun save(report: Report): Report {
        val reportJpaEntity = ReportMapper.toJpaEntity(report)

        return ReportMapper.toDomainEntity(reportJpaRepository.save(reportJpaEntity))
    }

}
