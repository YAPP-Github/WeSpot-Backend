package com.wespot.comment.repository

import com.wespot.comment.PostCommentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostCommentJpaRepository : JpaRepository<PostCommentEntity, Long> {

    fun findAllByPostId(postId: Long): List<PostCommentEntity>

}
