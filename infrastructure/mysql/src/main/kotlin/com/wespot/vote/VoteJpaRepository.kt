package com.wespot.vote

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface VoteJpaRepository : JpaRepository<VoteJpaEntity, Long> {

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

    @Query(
        """
        SELECT v
        FROM VoteJpaEntity v
        WHERE v.schoolId = :schoolId
        AND v.grade = :grade
        AND v.classNumber = :classNumber
        AND v.id < :cursorId
        ORDER BY v.date DESC
        LIMIT :limit
    """
    )
    fun findAllBySchoolIdAndGradeAndClassNumberOrderByDateDesc(
        schoolId: Long,
        grade: Int,
        classNumber: Int,
        cursorId: Long,
        limit: Long
    ): List<VoteJpaEntity>

}
