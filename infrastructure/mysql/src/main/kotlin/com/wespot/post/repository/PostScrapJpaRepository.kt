package com.wespot.post.repository

import com.wespot.post.PostScrapEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostScrapJpaRepository : JpaRepository<PostScrapEntity, Long> {

    fun findAllByPostIdInAndUserId(postIds: List<Long>, userId: Long): List<PostScrapEntity>

    fun findAllByUserId(userId: Long): List<PostScrapEntity>

    fun findByPostIdAndUserId(postId: Long, userId: Long): PostScrapEntity?

    fun deleteByPostId(postId: Long)

    fun deleteByUserId(userId: Long)

}
