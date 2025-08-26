package com.wespot.report.service

import com.wespot.report.dto.ReportReasonResponse
import com.wespot.report.port.`in`.ReportReasonUseCase
import com.wespot.report.port.out.ReportReasonPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReportReasonService(
    private val reportReasonPort: ReportReasonPort
) : ReportReasonUseCase {

    @Transactional(readOnly = true)
    override fun getReportReasons(): List<ReportReasonResponse> {
        return reportReasonPort.findAll()
            .map { ReportReasonResponse.from(it) }
    }

}
