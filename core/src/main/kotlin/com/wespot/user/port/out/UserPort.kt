package com.wespot.user.port.out

import com.wespot.user.User
import org.springframework.stereotype.Repository

interface UserPort {

    fun findById(id: Long): User?

    fun findAllBySchoolIdAndGradeAndGroupNumber(
        schoolId: Long,
        grade: Int,
        groupNumber: Int
    ): List<User>

    fun findIdsByIdIn(ids: List<Long>): List<Long>

    fun getByEmail(userEmail: String): User?

}
