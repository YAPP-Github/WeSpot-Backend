package com.wespot.post.adapter

import com.wespot.post.PostLike
import com.wespot.post.mapper.PostLikeMapper
import com.wespot.post.port.out.PostLikePort
import com.wespot.post.repository.PostLikeJpaRepository
import org.springframework.stereotype.Repository

@Repository
class PostLikeAdapter(
    private val postLikeJpaRepository: PostLikeJpaRepository
) : PostLikePort {

    override fun deleteById(id: Long) {
        postLikeJpaRepository.deleteById(id)
    }

    override fun save(postLike: PostLike): PostLike {
        val postLikeEntity = PostLikeMapper.toEntity(postLike)
        val savedPostLikeEntity = postLikeJpaRepository.save(postLikeEntity)

        return PostLikeMapper.toDomain(savedPostLikeEntity)
    }

    override fun findByPostIdAndUserId(postId: Long, userId: Long): PostLike? {
        return postLikeJpaRepository.findByPostIdAndUserId(postId = postId, userId = userId)
            ?.let { PostLikeMapper.toDomain(it) }
    }

    override fun deleteByPostId(postId: Long) {
        postLikeJpaRepository.deleteByPostId(postId)
    }
}
