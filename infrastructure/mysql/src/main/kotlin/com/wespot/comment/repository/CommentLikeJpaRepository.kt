package com.wespot.comment.repository

import com.wespot.comment.CommentLikeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CommentLikeJpaRepository : JpaRepository<CommentLikeEntity, Long> {
}
