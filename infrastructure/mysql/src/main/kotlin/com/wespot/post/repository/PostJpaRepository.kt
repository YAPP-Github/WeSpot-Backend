package com.wespot.post.repository

import com.wespot.post.PostEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PostJpaRepository : JpaRepository<PostEntity, Long> {

    fun findAllByTitleContaining(title: String): List<PostEntity>

    fun findAllByDescriptionContaining(description: String): List<PostEntity>

    fun findByCategoryId(categoryId: Long): List<PostEntity>

    fun findByCategoryIdIn(categoryIds: List<Long>): List<PostEntity>

    fun findAllByUserId(authorId: Long): List<PostEntity>

    fun findAllByIdIn(postIds: List<Long>): List<PostEntity>

    fun findAllByOrderByBaseEntityCreatedAtDesc(pageable: Pageable): List<PostEntity>

}
