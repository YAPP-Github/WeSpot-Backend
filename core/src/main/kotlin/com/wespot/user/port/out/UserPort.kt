package com.wespot.user.port.out

import com.wespot.user.User

interface UserPort {

    fun findById(userId: Long): User?

    fun findAllBySchoolIdAndGradeAndClassNumber(
        schoolId: Long,
        grade: Int,
        groupNumber: Int
    ): List<User>

    fun findIdsByIdIn(ids: List<Long>): List<Long>

    fun findByEmail(userEmail: String): User?

    fun save(user: User): User


}
