package com.wespot.comment.repository

import com.wespot.comment.PostCommentReportEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostCommentReportJpaRepository : JpaRepository<PostCommentReportEntity, Long> {

    fun findByPostCommentIdAndUserId(postCommentId: Long, userId: Long): PostCommentReportEntity?

    fun deleteByPostCommentIdIn(postCommentId: List<Long>)

}
