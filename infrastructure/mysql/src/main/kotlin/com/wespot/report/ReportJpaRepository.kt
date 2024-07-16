package com.wespot.report

import org.springframework.data.jpa.repository.JpaRepository

interface ReportJpaRepository:JpaRepository<ReportJpaEntity, Long> {
}
