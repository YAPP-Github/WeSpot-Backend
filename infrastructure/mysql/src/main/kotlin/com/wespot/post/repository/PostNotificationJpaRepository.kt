package com.wespot.post.repository

import com.wespot.post.PostNotificationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostNotificationJpaRepository : JpaRepository<PostNotificationEntity, Long> {

    fun findAllByPostId(postId: Long): List<PostNotificationEntity>

}
