package com.wespot.post.adapter

import com.wespot.post.PostCategory
import com.wespot.post.mapper.PostCategoryMapper
import com.wespot.post.port.out.PostCategoryPort
import com.wespot.post.repository.PostCategoryJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PostCategoryAdapter(
    private val postCategoryJpaRepository: PostCategoryJpaRepository
) : PostCategoryPort {

    override fun findAll(): List<PostCategory> {
        return postCategoryJpaRepository.findAll()
            .map { PostCategoryMapper.toDomain(it) }
    }

    override fun findById(categoryId: Long): PostCategory? {
        return postCategoryJpaRepository.findByIdOrNull(id = categoryId)
            ?.let { PostCategoryMapper.toDomain(entity = it) }
    }

}
