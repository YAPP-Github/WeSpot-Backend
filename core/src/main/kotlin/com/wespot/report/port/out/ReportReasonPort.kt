package com.wespot.report.port.out

import com.wespot.report.ReportReason

interface ReportReasonPort {

    fun findAll(): List<ReportReason>

    fun findById(reportReasonId: Long): ReportReason?

}
