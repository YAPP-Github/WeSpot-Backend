package com.wespot.report.port.out

import com.wespot.report.Report
import com.wespot.report.ReportType

interface ReportPort {

    fun findAllByReceiverIdAndReportType(receiverId: Long, reportType: ReportType): List<Report>

    fun save(report: Report): Report

}
