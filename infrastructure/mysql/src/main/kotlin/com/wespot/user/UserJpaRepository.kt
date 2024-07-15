package com.wespot.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserJpaRepository : JpaRepository<UserJpaEntity, Long> {

    fun findAllBySchoolIdAndGradeAndGroupNumber(
        schoolId: Long,
        grade: Int,
        groupNumber: Int
    ): List<UserJpaEntity>

    @Query("SELECT u.id FROM UserJpaEntity u WHERE u.id IN :ids")
    fun findIdsByIdIn(@Param("ids") ids: List<Long>): List<Long>

}