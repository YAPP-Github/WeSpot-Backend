package com.wespot.school

import org.springframework.data.jpa.repository.JpaRepository

interface SchoolJpaRepository : JpaRepository<SchoolJpaEntity, Long> {
}