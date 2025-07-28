package com.wespot.post.repository

import com.wespot.post.PostProfileEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostProfileJpaRepository : JpaRepository<PostProfileEntity, Long> {

    fun findByUserId(userId: Long): PostProfileEntity?

    fun findAllByUserIdIn(userIds: List<Long>): List<PostProfileEntity>
    fun userId(userId: Long): MutableList<PostProfileEntity>

}
