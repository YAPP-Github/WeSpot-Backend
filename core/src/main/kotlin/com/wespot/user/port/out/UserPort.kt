package com.wespot.user.port.out

import com.wespot.user.Role
import com.wespot.user.User
import org.springframework.data.domain.Pageable

interface UserPort {

    fun findById(userId: Long): User?

    fun findAllBySchoolIdAndGradeAndClassNumber(
        schoolId: Long,
        grade: Int,
        classNumber: Int
    ): List<User>

    fun findIdsByIdIn(ids: List<Long>): List<Long>

    fun findByEmail(userEmail: String): User?

    fun save(user: User): User

    fun searchUsers(
        name: String,
        cursorName: String?,
        cursorSchoolName: String?,
        cursorSchoolTypeOrder: Int?,
        cursorId: Long?,
        pageable: Pageable
    ): List<User>
}
