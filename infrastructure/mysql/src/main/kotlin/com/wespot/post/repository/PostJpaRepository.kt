package com.wespot.post.repository

import com.wespot.post.PostEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PostJpaRepository : JpaRepository<PostEntity, Long> {

    fun findByTitleContainingOrderByBaseEntityCreatedAtDesc(
        title: String,
    ): List<PostEntity>

    fun findAllByDescriptionContainingOrderByBaseEntityCreatedAtDesc(
        description: String,
    ): List<PostEntity>

    fun findByCategoryIdAndIdLessThanOrderByBaseEntityCreatedAtDesc(
        categoryId: Long,
        cursorId: Long,
        pageable: Pageable

    ): List<PostEntity>

    fun findByCategoryIdInAndIdLessThanOrderByBaseEntityCreatedAtDesc(
        categoryIds: List<Long>,
        cursorId: Long,
        pageable: Pageable
    ): List<PostEntity>

    fun findAllByUserIdAndIdLessThanOrderByBaseEntityCreatedAtDesc(
        userId: Long,
        cursorId: Long,
        pageable: Pageable
    ): List<PostEntity>

    fun findAllByIdInAndIdLessThanOrderByBaseEntityCreatedAtDesc(
        postIds: List<Long>,
        cursorId: Long,
        pageable: Pageable
    ): List<PostEntity>

    fun findAllByIdLessThanOrderByBaseEntityCreatedAtDesc(
        cursorId: Long,
        pageable: Pageable
    ): List<PostEntity>

}
