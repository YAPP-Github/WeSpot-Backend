package com.wespot.report

import org.springframework.data.jpa.repository.JpaRepository

interface ReportReasonJpaRepository : JpaRepository<ReportReasonJpaEntity, Long> {
}
