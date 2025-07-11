package com.wespot.post.adapter

import com.wespot.post.PostScrap
import com.wespot.post.mapper.PostScrapMapper
import com.wespot.post.port.out.PostScrapPort
import com.wespot.post.repository.PostScrapJpaRepository
import org.springframework.stereotype.Repository

@Repository
class PostScrapAdapter(
    private val postScrapJpaRepository: PostScrapJpaRepository
) : PostScrapPort {

    override fun findAllByUserId(userId: Long): List<PostScrap> {
        return postScrapJpaRepository.findAllByUserId(userId)
            .map { PostScrapMapper.toDomain(it) }
    }

    override fun deleteById(id: Long) {
        postScrapJpaRepository.deleteById(id)
    }

    override fun save(postScrap: PostScrap): PostScrap {
        val postScrapEntity = PostScrapMapper.toEntity(postScrap)
        val savedPostScrapEntity = postScrapJpaRepository.save(postScrapEntity)

        return PostScrapMapper.toDomain(savedPostScrapEntity)
    }

    override fun findByPostIdAndUserId(postId: Long, userId: Long): PostScrap? {
        return postScrapJpaRepository.findByPostIdAndUserId(postId = postId, userId = userId)
            ?.let { PostScrapMapper.toDomain(it) }
    }

}
