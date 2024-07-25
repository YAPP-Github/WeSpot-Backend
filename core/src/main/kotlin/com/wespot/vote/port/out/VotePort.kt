package com.wespot.vote.port.out

import com.wespot.vote.Vote
import java.time.LocalDate

interface VotePort {

    fun findTop1BySchoolIdAndGradeAndClassNumberOrderByDateDescExcludeBallots(
        schoolId: Long,
        grade: Int,
        classNumber: Int
    ): Vote?

    fun existsBySchoolIdAndGradeAndClassNumberAndDate(
        schoolId: Long,
        grade: Int,
        classNumber: Int,
        date: LocalDate
    ): Boolean

    fun existsById(id: Long): Boolean

    fun findBySchoolIdAndGradeAndClassNumberAndDate(
        schoolId: Long,
        grade: Int,
        classNumber: Int,
        date: LocalDate
    ): Vote?

    fun save(vote: Vote): Vote

    fun findAllBySchoolIdAndGradeAndClassNumberByOrderByDateDesc(
        schoolId: Long,
        grade: Int,
        classNumber: Int
    ): List<Vote>


}
