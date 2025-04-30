package com.wespot.user.adapter

import com.wespot.school.SchoolJpaEntity
import com.wespot.school.SchoolJpaRepository
import com.wespot.user.User
import com.wespot.user.WithdrawalStatus
import com.wespot.user.entity.UserJpaEntity
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
    private val schoolJpaRepository: SchoolJpaRepository,
) : UserPort {

    override fun existsBySchoolIdAndGradeAndClassNumber(schoolId: Long, grade: Int, classNumber: Int): Boolean {
        return userJpaRepository.existsBySchoolIdAndGradeAndClassNumber(schoolId, grade, classNumber)
    }

    override fun findByEmail(userEmail: String): User? {
        return userJpaRepository.findByEmail(userEmail)
            ?.let { UserMapper.mapToDomainEntity(userJpaEntity = it, schoolJpaEntity = getBySchool(it)) }
    }

    private fun getBySchool(it: UserJpaEntity): SchoolJpaEntity {
        return schoolJpaRepository.findByIdOrNull(it.schoolId) ?: throw IllegalArgumentException("학교를 찾을 수 없습니다.")
    }

    override fun save(user: User): User {
        return userJpaRepository.save(UserMapper.mapToJpaEntity(user))
            .let { UserMapper.mapToDomainEntity(userJpaEntity = it, schoolJpaEntity = getBySchool(it)) }
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
        val searchUsers = userJpaRepository.searchUsers(
            name = name,
            cursorName = cursorName,
            cursorSchoolName = cursorSchoolName,
            cursorSchoolTypeOrder = cursorSchoolTypeOrder,
            cursorId = cursorId,
            pageable = pageable,
            loginUserId = loginUserId
        )
        val schoolMap = getSchoolMapsByUsers(searchUsers)
        return searchUsers
            .map { userJpaEntity ->
                UserMapper.mapToDomainEntity(
                    userJpaEntity = userJpaEntity,
                    schoolJpaEntity = schoolMap[userJpaEntity.schoolId]!!
                )
            }
            .toList()
    }

    private fun getSchoolMapsByUsers(users: List<UserJpaEntity>): Map<Long, SchoolJpaEntity> {
        val schoolIds = users.map { it.schoolId }
        return schoolJpaRepository.findAllByIdIn(schoolIds)
            .associateBy { it.id }
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
        val users =
            userJpaRepository.findAllByWithdrawalRequestAtBeforeAndWithdrawalStatus(
                withdrawalRequestAt,
                withdrawalStatus
            )
        val schoolMap = getSchoolMapsByUsers(users = users)
        return users
            .map { userJpaEntity ->
                UserMapper.mapToDomainEntity(
                    userJpaEntity = userJpaEntity,
                    schoolJpaEntity = schoolMap[userJpaEntity.schoolId]!!
                )
            }
    }

    override fun findById(userId: Long): User? {
        return userJpaRepository.findByIdOrNull(userId)
            ?.let { UserMapper.mapToDomainEntity(userJpaEntity = it, schoolJpaEntity = getBySchool(it)) }
    }

    override fun findAllBySchoolIdAndGradeAndClassNumber(
        schoolId: Long,
        grade: Int,
        classNumber: Int
    ): List<User> {
        val users = userJpaRepository.findAllBySchoolIdAndGradeAndClassNumber(
            schoolId,
            grade,
            classNumber
        )
        val schoolMaps = getSchoolMapsByUsers(users)

        return users
            .map { userJpaEntity ->
                UserMapper.mapToDomainEntity(
                    userJpaEntity = userJpaEntity,
                    schoolJpaEntity = schoolMaps[userJpaEntity.schoolId]!!
                )
            }
            .toList()
    }

    override fun findByIdIn(ids: List<Long>): List<User> {
        val users = userJpaRepository.findByIdIn(ids)
        val schoolMap = getSchoolMapsByUsers(users = users)

        return users.map {
            UserMapper.mapToDomainEntity(
                userJpaEntity = it,
                schoolJpaEntity = schoolMap[it.schoolId]!!
            )
        }
    }

    override fun findAll(): List<User> {
        val users = userJpaRepository.findAll()
        val schoolMap = getSchoolMapsByUsers(users = users)
        return users.map {
            UserMapper.mapToDomainEntity(
                userJpaEntity = it,
                schoolJpaEntity = schoolMap[it.schoolId]!!
            )
        }
    }

}
