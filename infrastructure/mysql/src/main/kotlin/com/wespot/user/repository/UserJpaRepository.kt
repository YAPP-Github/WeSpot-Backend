package com.wespot.user.repository

import com.wespot.user.WithdrawalStatus
import com.wespot.user.entity.UserJpaEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface UserJpaRepository : JpaRepository<UserJpaEntity, Long> {

    fun findByEmail(email: String): UserJpaEntity?

    @Query(
        """
        SELECT u
        FROM UserJpaEntity u
        WHERE u.schoolId = :schoolId
        AND u.grade = :grade
        AND u.classNumber = :classNumber
        AND u.withdrawalStatus != 'WITHDRAWN'
        AND u.restriction.messageRestrictionType = 'NONE'
        AND u.restriction.voteRestrictionType = 'NONE'
        """
    )
    fun findAllBySchoolIdAndGradeAndClassNumber(
        schoolId: Long,
        grade: Int,
        classNumber: Int
    ): List<UserJpaEntity>

    fun findByIdIn(ids: List<Long>): List<UserJpaEntity>

    fun existsBySchoolIdAndGradeAndClassNumber(
        schoolId: Long,
        grade: Int,
        classNumber: Int
    ): Boolean

    @Query(
        """
        SELECT u FROM UserJpaEntity u
        LEFT JOIN SchoolJpaEntity s ON u.schoolId = s.id
        WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))
        AND u.id <> :loginUserId
        AND u.id NOT IN :blockedUserIds
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
        AND u.withdrawalStatus != 'WITHDRAWN'
        AND u.restriction.messageRestrictionType = 'NONE'
        AND u.restriction.voteRestrictionType = 'NONE'
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
        @Param("loginUserId") loginUserId: Long,
        @Param("blockedUserIds") blockedUserIds: List<Long>,
        pageable: Pageable
    ): List<UserJpaEntity>

    @Query(
        """
        SELECT COUNT(u)
        FROM UserJpaEntity u
        LEFT JOIN SchoolJpaEntity s ON u.schoolId = s.id
        AND u.id <> :loginUserId
        AND u.id NOT IN :blockedUserIds
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
            AND u.withdrawalStatus != 'WITHDRAWN'
            AND u.restriction.messageRestrictionType = 'NONE'
            AND u.restriction.voteRestrictionType = 'NONE'
        """
    )
    fun countUsersAfterCursor(
        @Param("name") name: String,
        @Param("cursorName") cursorName: String?,
        @Param("cursorSchoolName") cursorSchoolName: String?,
        @Param("cursorSchoolTypeOrder") cursorSchoolTypeOrder: Int?,
        @Param("cursorId") cursorId: Long?,
        @Param("loginUserId") loginUserId: Long,
        @Param("blockedUserIds") blockedUserIds: List<Long>,
    ): Long

    fun countBySchoolIdAndGradeAndClassNumber(
        schoolId: Long,
        grade: Int,
        classNumber: Int
    ): Long


    fun findAllByWithdrawalRequestAtBeforeAndWithdrawalStatus(
        withdrawalRequestAt: LocalDateTime,
        withdrawalStatus: WithdrawalStatus
    ): List<UserJpaEntity>

    fun findByName(name: String): UserJpaEntity?

    fun findAllByIdIn(userIds: List<Long>): List<UserJpaEntity>

}
