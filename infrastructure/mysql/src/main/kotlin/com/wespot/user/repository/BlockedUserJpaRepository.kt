package com.wespot.user.repository

import com.wespot.user.entity.BlockedUserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface BlockedUserJpaRepository: JpaRepository<BlockedUserJpaEntity, Long> {

    fun existsByBlockerIdAndBlockedId(blockerId: Long, blockedId: Long): Boolean

    fun deleteByBlockerIdAndBlockedId(blockerId: Long, blockedId: Long)

    fun findAllByBlockerId(blockerId: Long): List<BlockedUserJpaEntity>

    @Query("SELECT bu.createdAt FROM BlockedUserJpaEntity bu WHERE bu.blockerId = :blockerId AND bu.blockedId = :blockedId")
    fun findBlockedTime(@Param("blockerId") blockerId: Long, @Param("blockedId") blockedId: Long): LocalDateTime?


}
