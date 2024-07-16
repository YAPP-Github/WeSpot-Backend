package com.wespot.user

import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository
) : UserPort {

    override fun findById(id: Long): User? {
        return userJpaRepository.findById(id)
            .getOrNull()
            ?.let { userJpaEntity ->
                UserMapper.mapToDomainEntity(userJpaEntity)
            }
    }

    override fun findAllBySchoolIdAndGradeAndGroupNumber(
        schoolId: Long,
        grade: Int,
        groupNumber: Int
    ): List<User> {
        return userJpaRepository.findAllBySchoolIdAndGradeAndGroupNumber(
            schoolId,
            grade,
            groupNumber
        ).stream()
            .map { userJpaEntity -> UserMapper.mapToDomainEntity(userJpaEntity) }
            .toList()
    }

    override fun findIdsByIdIn(ids: List<Long>): List<Long> {
        return userJpaRepository.findIdsByIdIn(ids)
    }

}
