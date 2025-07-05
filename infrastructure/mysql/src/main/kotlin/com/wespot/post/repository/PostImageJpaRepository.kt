package com.wespot.post.repository

import com.wespot.post.PostImageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostImageJpaRepository : JpaRepository<PostImageEntity, Long> {
}
