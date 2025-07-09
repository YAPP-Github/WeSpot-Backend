package com.wespot.comment.repository

import com.wespot.comment.PostCommentLikeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostCommentLikeJpaRepository : JpaRepository<PostCommentLikeEntity, Long> {
}
