package com.wespot.post.repository

import com.wespot.post.PostCategory
import com.wespot.post.PostCategoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostCategoryJpaRepository : JpaRepository<PostCategoryEntity, Long> {

    fun findAllByIdIn(categoryIds: List<Long>): List<PostCategoryEntity>

    fun findAlLByMajorCategoryName(majorCategoryName: String): List<PostCategoryEntity>

    fun findAllByName(name: String): List<PostCategoryEntity>

}
