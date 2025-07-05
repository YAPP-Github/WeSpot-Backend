package com.wespot.comment.repository

import com.wespot.comment.CommentReportEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CommentReportJpaRepository : JpaRepository<CommentReportEntity, Long> {
}
