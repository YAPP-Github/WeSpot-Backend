package com.wespot.post.adapter

import com.wespot.post.PostBlock
import com.wespot.post.mapper.PostBlockMapper
import com.wespot.post.port.out.PostBlockPort
import com.wespot.post.repository.PostBlockJpaRepository
import org.springframework.stereotype.Repository

@Repository
class PostBlockAdapter(
    private val postBlockJpaRepository: PostBlockJpaRepository
) : PostBlockPort {

    override fun findByPostIdAndUserId(postId: Long, userId: Long): PostBlock? {
        return postBlockJpaRepository.findByPostIdAndUserId(postId, userId)?.let { PostBlockMapper.toDomain(it) }
    }

    override fun deleteById(id: Long) {
        postBlockJpaRepository.deleteById(id)
    }

    override fun save(postBlock: PostBlock): PostBlock {
        val postBlockEntity = PostBlockMapper.toEntity(postBlock)
        val savedPostBlockEntity = postBlockJpaRepository.save(postBlockEntity)
        return PostBlockMapper.toDomain(savedPostBlockEntity)
    }

    override fun deleteByPostId(postId: Long) {
        postBlockJpaRepository.deleteByPostId(postId)
    }

    override fun findAllByUserId(userId: Long): List<PostBlock> {
        return postBlockJpaRepository.findAllByUserId(userId)
            .map { PostBlockMapper.toDomain(it) }
    }

}
