package com.wespot.vote

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface VoteJpaRepository : JpaRepository<VoteJpaEntity, Long> {

    fun findTop1BySchoolIdAndGradeAndClassNumberOrderByDateDesc(
        schoolId: Long,
        grade: Int,
        classNumber: Int,
    ): VoteJpaEntity?

    fun existsBySchoolIdAndGradeAndClassNumberAndDate(
        schoolId: Long,
        grade: Int,
        classNumber: Int,
        date: LocalDate
    ): Boolean

    fun findBySchoolIdAndGradeAndClassNumberAndDate(
        schoolId: Long,
        grade: Int,
        classNumber: Int,
        date: LocalDate
    ): VoteJpaEntity?

    fun findAllBySchoolIdAndGradeAndClassNumberOrderByDateDesc(
        schoolId: Long,
        grade: Int,
        classNumber: Int
    ): List<VoteJpaEntity>

}
