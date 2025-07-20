package com.wespot.post.repository

import com.wespot.post.PostReportEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostReportJpaRepository : JpaRepository<PostReportEntity, Long> {

    fun findByPostIdAndUserId(postId: Long, userId: Long): PostReportEntity?

    fun deleteByPostId(postId: Long)

}
