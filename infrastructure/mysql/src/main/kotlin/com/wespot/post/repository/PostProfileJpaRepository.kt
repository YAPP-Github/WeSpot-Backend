package com.wespot.post.repository

import com.wespot.post.PostProfileEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostProfileJpaRepository : JpaRepository<PostProfileEntity, Long> {
}
