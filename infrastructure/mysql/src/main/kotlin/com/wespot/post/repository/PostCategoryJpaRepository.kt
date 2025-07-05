package com.wespot.post.repository

import com.wespot.post.PostCategory
import org.springframework.data.jpa.repository.JpaRepository

interface PostCategoryJpaRepository : JpaRepository<PostCategory, Long> {
}
