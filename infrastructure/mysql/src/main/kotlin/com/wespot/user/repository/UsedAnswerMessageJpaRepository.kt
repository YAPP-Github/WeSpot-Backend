package com.wespot.user.repository

import com.wespot.user.entity.message.UsedAnswerMessageJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UsedAnswerMessageJpaRepository : JpaRepository<UsedAnswerMessageJpaEntity, Long> {

    fun existsByUserId(userId: Long): Boolean

    fun findByUserId(userId: Long): UsedAnswerMessageJpaEntity?

}
