package com.wespot.post.adapter

import com.wespot.post.Post
import com.wespot.post.PostImages
import com.wespot.post.mapper.PostImageMapper
import com.wespot.post.mapper.PostMapper
import com.wespot.post.port.out.PostPort
import com.wespot.post.repository.PostImageJpaRepository
import com.wespot.post.repository.PostJpaRepository
import org.springframework.stereotype.Repository

@Repository
class PostAdapter(
    private val postJpaRepository: PostJpaRepository,
    private val postImageJpaRepository: PostImageJpaRepository,
) : PostPort {

    override fun save(post: Post): Post {
        val postEntity = PostMapper.toEntity(post)
        val savedPostEntity = postJpaRepository.save(postEntity)
        val savedPostImages: PostImages? = post.images?.postImages
            ?.map { PostImageMapper.toEntity(it) }
            ?.let { postImageJpaRepository.saveAll(it) }
            ?.map { PostImageMapper.toDomain(it) }
            ?.let { PostImages(it) }

        return PostMapper.toDomain(
            entity = savedPostEntity,
            postCategory = post.category,
            user = post.user,
            postImages = savedPostImages
        )
    }

}
