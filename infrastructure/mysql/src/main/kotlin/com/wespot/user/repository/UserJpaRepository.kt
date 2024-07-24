package com.wespot.user.repository

import com.wespot.user.Role
import com.wespot.user.entity.UserJpaEntity
import org.springframework.data.domain.Pageable
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

    @Query(
        """
        SELECT u FROM UserJpaEntity u
        LEFT JOIN SchoolJpaEntity s ON u.schoolId = s.id
        WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))
        AND (
            :cursorId IS NULL OR (
                (:cursorName IS NULL OR u.name > :cursorName) OR
                (u.name = :cursorName AND (:cursorSchoolName IS NULL OR s.name > :cursorSchoolName)) OR
                (u.name = :cursorName AND s.name = :cursorSchoolName AND
                    CASE
                        WHEN s.schoolType = 'MIDDLE' THEN 1
                        WHEN s.schoolType = 'HIGH' THEN 2
                    END > :cursorSchoolTypeOrder) OR
                (u.name = :cursorName AND s.name = :cursorSchoolName AND
                    CASE
                        WHEN s.schoolType = 'MIDDLE' THEN 1
                        WHEN s.schoolType = 'HIGH' THEN 2
                    END = :cursorSchoolTypeOrder AND u.id > :cursorId)
            )
        )
        ORDER BY u.name ASC, s.name ASC,
        CASE
            WHEN s.schoolType = 'MIDDLE' THEN 1
            WHEN s.schoolType = 'HIGH' THEN 2
        END ASC, u.id ASC
        """
    )
    fun searchUsers(
        @Param("name") name: String,
        @Param("cursorName") cursorName: String?,
        @Param("cursorSchoolName") cursorSchoolName: String?,
        @Param("cursorSchoolTypeOrder") cursorSchoolTypeOrder: Int?,
        @Param("cursorId") cursorId: Long?,
        pageable: Pageable
    ): List<UserJpaEntity>


}
