package com.wespot.report

import com.wespot.report.port.out.ReportStatePort

class ReportPersistenceAdapter(
    private val reportJpaRepository: ReportJpaRepository
) : ReportStatePort {
}