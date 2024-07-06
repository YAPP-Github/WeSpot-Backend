package com.wespot.report.service

import com.wespot.report.port.`in`.ReportUseCase

class ReportService(
    private val reportUseCase: ReportUseCase
) : ReportUseCase {
}