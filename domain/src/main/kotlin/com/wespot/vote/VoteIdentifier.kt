package com.wespot.vote

import com.wespot.user.User
import java.time.LocalDate

data class VoteIdentifier(
    val schoolId: Long,
    val grade: Int,
    val classNumber: Int,
    val date: LocalDate,
) {

    companion object {

        fun of(user: User, date: LocalDate): VoteIdentifier {
            return VoteIdentifier(
                schoolId = user.school.id,
                grade = user.grade,
                classNumber = user.classNumber,
                date = date
            )
        }

    }

    fun isSameClass(otherVoteIdentifier: VoteIdentifier) =
        schoolId == otherVoteIdentifier.schoolId &&
            grade == otherVoteIdentifier.grade &&
            classNumber == otherVoteIdentifier.classNumber

}
