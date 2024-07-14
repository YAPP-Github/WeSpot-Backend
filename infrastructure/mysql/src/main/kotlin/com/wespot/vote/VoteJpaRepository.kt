package com.wespot.vote

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface VoteJpaRepository : JpaRepository<VoteJpaEntity, Long> {

    fun findBySchoolIdAndGradeAndGroupNumberAndDate(
        schoolId: Long,
        grade: Int,
        groupNumber: Int,
        date: LocalDate
    ): VoteJpaEntity?

}