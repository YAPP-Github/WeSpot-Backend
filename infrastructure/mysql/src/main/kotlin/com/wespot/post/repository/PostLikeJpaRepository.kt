package com.wespot.post.repository

import com.wespot.post.PostLikeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostLikeJpaRepository : JpaRepository<PostLikeEntity, Long> {

    fun findAllByPostIdInAndUserId(postIds: List<Long>, userId: Long): List<PostLikeEntity>

    fun findByPostIdAndUserId(postId: Long, userId: Long): PostLikeEntity?

    fun deleteByPostId(postId: Long)

    fun deleteByUserId(userId: Long)

}
