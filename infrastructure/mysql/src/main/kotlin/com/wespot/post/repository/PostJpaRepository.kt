package com.wespot.post.repository

import com.wespot.post.PostEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostJpaRepository : JpaRepository<PostEntity, Long> {

    fun findAllByTitleContaining(title: String): List<PostEntity>

    fun findAllByDescriptionContaining(description: String): List<PostEntity>

}
