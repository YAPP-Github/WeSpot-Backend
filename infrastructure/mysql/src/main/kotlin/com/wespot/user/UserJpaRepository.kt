package com.wespot.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserJpaEntity, Long> {

    fun findAllBySchoolIdAndGradeAndGroupNumber(
        schoolId: Long,
        grade: Int,
        groupNumber: Int
    ): List<UserJpaEntity>

}