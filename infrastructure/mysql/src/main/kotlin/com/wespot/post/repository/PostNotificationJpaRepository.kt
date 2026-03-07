package com.wespot.post.repository

import com.wespot.post.PostNotificationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostNotificationJpaRepository : JpaRepository<PostNotificationEntity, Long> {

    fun findAllByPostId(postId: Long): List<PostNotificationEntity>

    fun findByPostIdAndUserId(postId: Long, userId: Long): PostNotificationEntity?

    fun findAllByPostIdInAndUserId(postIds: List<Long>, userId: Long): List<PostNotificationEntity>

    fun deleteByPostId(postId: Long)

    fun deleteByUserId(userId: Long)

}
