package com.wespot.vote.fixture

import com.wespot.vote.VoteJpaEntity
import java.time.LocalDate

object VoteJpaEntityFixture {

    fun create() = VoteJpaEntity(
        id = 1L,
        schoolId = 1L,
        grade = 1,
        classNumber = 1,
        voteNumber = 0,
        date = LocalDate.now(),
    )

    fun createWithSchoolIdAndGradeAndClassNumber(schoolId: Long, grade: Int, classNumber: Int) = VoteJpaEntity(
        id = 1L,
        schoolId = schoolId,
        grade = grade,
        classNumber = classNumber,
        voteNumber = 0,
        date = LocalDate.now()
    )


}
