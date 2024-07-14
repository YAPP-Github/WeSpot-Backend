package com.wespot.user

import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Repository

@Repository
class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository
) : UserPort {

    override fun findById(id: Long): User? {
        return userJpaRepository.findById(id)?.let { };
    }

    override fun findAllBySchoolIdAndGradeAndGroupNumber(
        schoolId: Long,
        grade: Int,
        groupNumber: Int
    ): List<User> {

    }

}