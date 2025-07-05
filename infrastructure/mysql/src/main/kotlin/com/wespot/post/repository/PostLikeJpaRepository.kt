package com.wespot.post.repository

import com.wespot.post.PostLikeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostLikeJpaRepository : JpaRepository<PostLikeEntity, Long> {

}
