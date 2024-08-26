package com.wespot.user.port.out

import com.wespot.user.User
import com.wespot.user.WithdrawalStatus
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

interface UserPort {

    fun existsBySchoolIdAndGradeAndClassNumber(
        schoolId: Long,
        grade: Int,
        classNumber: Int
    ): Boolean

    fun findById(userId: Long): User?

    fun findAllBySchoolIdAndGradeAndClassNumber(
        schoolId: Long,
        grade: Int,
        classNumber: Int
    ): List<User>

    fun findByIdIn(ids: List<Long>): List<User>

    fun findByEmail(userEmail: String): User?

    fun save(user: User): User

    fun searchUsers(
        name: String,
        cursorName: String?,
        cursorSchoolName: String?,
        cursorSchoolTypeOrder: Int?,
        cursorId: Long?,
        pageable: Pageable,
        loginUserId: Long,
    ): List<User>

    fun findAll(): List<User>

    fun countUsersAfterCursor(
        name: String,
        cursorName: String?,
        cursorSchoolName: String?,
        cursorSchoolTypeOrder: Int?,
        cursorId: Long?,
        loginUserId: Long,
    ): Long

    fun countBySchoolIdAndGradeAndClassNumber(
        schoolId: Long,
        grade: Int,
        classNumber: Int
    ): Long

    fun findAllByWithdrawalRequestAtBeforeAndWithdrawalStatus(
        withdrawalRequestAt: LocalDateTime,
        withdrawalStatus: WithdrawalStatus
    ): List<User>
}
