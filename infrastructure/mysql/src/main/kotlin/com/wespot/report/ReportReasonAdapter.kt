package com.wespot.report

import com.wespot.report.port.out.ReportReasonPort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class ReportReasonAdapter(
    private val reportReasonJpaRepository: ReportReasonJpaRepository
) : ReportReasonPort {

    override fun findAll(): List<ReportReason> {
        return reportReasonJpaRepository.findAll()
            .map { ReportReasonMapper.toDomain(it) }
    }

    override fun findById(reportReasonId: Long): ReportReason? {
        return reportReasonJpaRepository.findByIdOrNull(reportReasonId)
            ?.let { ReportReasonMapper.toDomain(it) }
    }

}
