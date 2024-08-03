package com.wespot.user.adapter

import com.wespot.user.User
import com.wespot.user.mapper.UserMapper
import com.wespot.user.port.out.UserPort
import com.wespot.user.repository.UserJpaRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

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
        pageable: Pageable
    ): List<User> {
        return userJpaRepository.searchUsers(
            name = name,
            cursorName = cursorName,
            cursorSchoolName = cursorSchoolName,
            cursorSchoolTypeOrder = cursorSchoolTypeOrder,
            cursorId = cursorId,
            pageable = pageable
        ).stream()
            .map { userJpaEntity -> UserMapper.mapToDomainEntity(userJpaEntity) }
            .toList()
    }

    override fun countUsersAfterCursor(
        name: String,
        cursorName: String?,
        cursorSchoolName: String?,
        cursorSchoolTypeOrder: Int?,
        cursorId: Long?
    ): Long {
        return userJpaRepository.countUsersAfterCursor(
            name = name,
            cursorName = cursorName,
            cursorSchoolName = cursorSchoolName,
            cursorSchoolTypeOrder = cursorSchoolTypeOrder,
            cursorId = cursorId
        )
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

    override fun findIdsByIdIn(ids: List<Long>): List<Long> {
        return userJpaRepository.findIdsByIdIn(ids)
    }

    override fun findAll(): List<User> {
        return userJpaRepository.findAll()
            .map { UserMapper.mapToDomainEntity(it) }
    }

}
