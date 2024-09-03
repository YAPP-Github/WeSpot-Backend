package com.wespot.user.adapter

import com.wespot.user.User
import com.wespot.user.WithdrawalStatus
import com.wespot.user.mapper.UserMapper
import com.wespot.user.port.out.UserPort
import com.wespot.user.repository.UserJpaRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional(readOnly = true)
@Repository
class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository,
) : UserPort {

    override fun existsBySchoolIdAndGradeAndClassNumber(schoolId: Long, grade: Int, classNumber: Int): Boolean {
        return userJpaRepository.existsBySchoolIdAndGradeAndClassNumber(schoolId, grade, classNumber)
    }

    override fun findByEmail(userEmail: String): User? {
        return userJpaRepository.findByEmail(userEmail)
            ?.let { UserMapper.mapToDomainEntity(it) }
    }

    override fun save(user: User): User {
        return userJpaRepository.save(UserMapper.mapToJpaEntity(user))
            .let { UserMapper.mapToDomainEntity(it) }
    }

    override fun searchUsers(
        name: String,
        cursorName: String?,
        cursorSchoolName: String?,
        cursorSchoolTypeOrder: Int?,
        cursorId: Long?,
        pageable: Pageable,
        loginUserId: Long,
    ): List<User> {
        return userJpaRepository.searchUsers(
            name = name,
            cursorName = cursorName,
            cursorSchoolName = cursorSchoolName,
            cursorSchoolTypeOrder = cursorSchoolTypeOrder,
            cursorId = cursorId,
            pageable = pageable,
            loginUserId = loginUserId
        ).stream()
            .map { userJpaEntity -> UserMapper.mapToDomainEntity(userJpaEntity) }
            .toList()
    }

    override fun countUsersAfterCursor(
        name: String,
        cursorName: String?,
        cursorSchoolName: String?,
        cursorSchoolTypeOrder: Int?,
        cursorId: Long?,
        loginUserId: Long,
    ): Long {
        return userJpaRepository.countUsersAfterCursor(
            name = name,
            cursorName = cursorName,
            cursorSchoolName = cursorSchoolName,
            cursorSchoolTypeOrder = cursorSchoolTypeOrder,
            cursorId = cursorId,
            loginUserId = loginUserId
        )
    }

    override fun countBySchoolIdAndGradeAndClassNumber(schoolId: Long, grade: Int, classNumber: Int): Long {
        return userJpaRepository.countBySchoolIdAndGradeAndClassNumber(schoolId, grade, classNumber)
    }

    override fun findAllByWithdrawalRequestAtBeforeAndWithdrawalStatus(
        withdrawalRequestAt: LocalDateTime,
        withdrawalStatus: WithdrawalStatus
    ): List<User> {
        return userJpaRepository.findAllByWithdrawalRequestAtBeforeAndWithdrawalStatus(
            withdrawalRequestAt,
            withdrawalStatus
        ).stream()
            .map { userJpaEntity -> UserMapper.mapToDomainEntity(userJpaEntity) }
            .toList()
    }

    override fun findById(userId: Long): User? {
        return userJpaRepository.findByIdOrNull(userId)
            ?.let { UserMapper.mapToDomainEntity(it) }
    }

    override fun findAllBySchoolIdAndGradeAndClassNumber(
        schoolId: Long,
        grade: Int,
        classNumber: Int
    ): List<User> {
        return userJpaRepository.findAllBySchoolIdAndGradeAndClassNumber(
            schoolId,
            grade,
            classNumber
        ).stream()
            .map { userJpaEntity -> UserMapper.mapToDomainEntity(userJpaEntity) }
            .toList()
    }

    override fun findByIdIn(ids: List<Long>): List<User> {
        return userJpaRepository.findByIdIn(ids)
            .map { UserMapper.mapToDomainEntity(it) }
    }

    override fun findAll(): List<User> {
        return userJpaRepository.findAll()
            .map { UserMapper.mapToDomainEntity(it) }
    }

}
