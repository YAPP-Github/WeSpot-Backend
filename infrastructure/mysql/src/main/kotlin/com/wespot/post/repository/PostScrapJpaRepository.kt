package com.wespot.post.repository

import com.wespot.post.PostScrapEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostScrapJpaRepository : JpaRepository<PostScrapEntity, Long> {
}
