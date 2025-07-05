package com.wespot.post.repository

import com.wespot.post.PostBlockEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostBlockJpaRepository : JpaRepository<PostBlockEntity, Long> {
}
