package com.wespot.comment.repository

import com.wespot.comment.PostCommentLikeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostCommentLikeJpaRepository : JpaRepository<PostCommentLikeEntity, Long> {

    fun existsByPostCommentIdAndUserId(postCommentId: Long, userId: Long): Boolean

    fun findAllByPostCommentIdInAndUserId(postCommentIds: List<Long>, userId: Long): List<PostCommentLikeEntity>

    fun findByPostCommentIdAndUserId(postCommentId: Long, userId: Long): PostCommentLikeEntity?

    fun deleteByPostCommentIdIn(postCommentIds: List<Long>)

}
