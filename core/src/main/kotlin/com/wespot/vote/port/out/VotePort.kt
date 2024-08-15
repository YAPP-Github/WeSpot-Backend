package com.wespot.vote.port.out

import com.wespot.vote.Vote
import java.time.LocalDate

interface VotePort {

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

    fun findAllBySchoolIdAndGradeAndClassNumberAndReceiverIdOrderByDateDesc(
        schoolId: Long,
        grade: Int,
        classNumber: Int,
        receiverId: Long,
        cursorId: Long,
        limit: Long
    ): List<Vote>

    fun findAllBySchoolIdAndGradeAndClassNumberAndSenderIdOrderByDateDesc(
        schoolId: Long,
        grade: Int,
        classNumber: Int,
        senderId: Long,
        cursorId: Long,
        limit: Long
    ): List<Vote>

}
