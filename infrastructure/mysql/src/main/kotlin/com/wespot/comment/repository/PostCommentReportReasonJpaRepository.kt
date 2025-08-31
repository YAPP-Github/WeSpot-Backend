package com.wespot.comment.repository

import com.wespot.comment.PostCommentReportReasonEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostCommentReportReasonJpaRepository : JpaRepository<PostCommentReportReasonEntity, Long> {

    fun findByPostCommentReportId(postCommentReportId: Long): List<PostCommentReportReasonEntity>

}
