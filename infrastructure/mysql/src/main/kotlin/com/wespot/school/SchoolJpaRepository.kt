package com.wespot.school

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SchoolJpaRepository : JpaRepository<SchoolJpaEntity, Long> {

    @Query("""
        SELECT s
        FROM SchoolJpaEntity s
        WHERE s.name LIKE %:keyword%
        AND s.id > :cursorId
        ORDER BY s.id ASC
    """)
    fun findAllByNameContainingAndIdGreaterThan(
        @Param("keyword") keyword: String,
        @Param("cursorId") cursorId: Long,
        pageable: Pageable
    ): List<SchoolJpaEntity>

    @Query("""
        SELECT COUNT(s)
        FROM SchoolJpaEntity s
        WHERE s.name LIKE %:keyword%
        AND s.id > :cursorId
    """)
    fun countByNameContainingAndIdGreaterThan(
        @Param("keyword") keyword: String,
        @Param("cursorId") cursorId: Long
    ): Long
}
