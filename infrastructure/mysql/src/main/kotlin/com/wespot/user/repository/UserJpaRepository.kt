package com.wespot.user.repository

import com.wespot.user.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserJpaRepository : JpaRepository<UserJpaEntity, Long> {

    fun findByEmail(email: String): UserJpaEntity?

    fun findAllBySchoolIdAndGradeAndClassNumber(
        schoolId: Long,
        grade: Int,
        classNumber: Int
    ): List<UserJpaEntity>

    @Query("SELECT u.id FROM UserJpaEntity u WHERE u.id IN :ids")
    fun findIdsByIdIn(@Param("ids") ids: List<Long>): List<Long>
}
