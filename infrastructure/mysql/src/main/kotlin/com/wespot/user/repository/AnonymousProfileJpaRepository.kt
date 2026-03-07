package com.wespot.user.repository

import com.wespot.user.entity.message.AnonymousProfileJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AnonymousProfileJpaRepository : JpaRepository<AnonymousProfileJpaEntity, Long> {

    fun findAllByOwnerIdAndReceiverId(ownerId: Long, receiverId: Long): List<AnonymousProfileJpaEntity>

    fun findByIdIn(ids: List<Long>): List<AnonymousProfileJpaEntity>

    fun deleteByOwnerIdOrReceiverId(ownerId: Long, receiverId: Long)

}
