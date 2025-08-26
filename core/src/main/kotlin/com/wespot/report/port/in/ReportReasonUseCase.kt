package com.wespot.report.port.`in`

import com.wespot.report.dto.ReportReasonResponse

interface ReportReasonUseCase {

    fun getReportReasons(): List<ReportReasonResponse>

}
