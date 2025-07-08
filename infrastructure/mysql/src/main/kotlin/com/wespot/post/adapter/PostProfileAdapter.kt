package com.wespot.post.adapter

import com.wespot.post.PostProfile
import com.wespot.post.mapper.PostProfileMapper
import com.wespot.post.port.out.PostProfilePort
import com.wespot.post.repository.PostProfileJpaRepository
import org.springframework.stereotype.Repository

@Repository
class PostProfileAdapter(
    private val postProfileJpaRepository: PostProfileJpaRepository
) : PostProfilePort {

    override fun findByUserId(userId: Long): PostProfile? {
        return postProfileJpaRepository.findByUserId(userId = userId)?.let { PostProfileMapper.toDomain(it) }
    }

    override fun save(postProfile: PostProfile): PostProfile {
        val postProfileEntity = PostProfileMapper.toEntity(postProfile = postProfile)
        val savedPostProfileEntity = postProfileJpaRepository.save(postProfileEntity)

        return PostProfileMapper.toDomain(savedPostProfileEntity)
    }
}
