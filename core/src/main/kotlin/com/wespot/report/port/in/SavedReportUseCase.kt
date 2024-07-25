package com.wespot.report.port.`in`

import com.wespot.report.dto.ReportRequest
import com.wespot.report.dto.ReportResponse

interface SavedReportUseCase {

    fun reportReceived(reportRequest: ReportRequest): ReportResponse

}
