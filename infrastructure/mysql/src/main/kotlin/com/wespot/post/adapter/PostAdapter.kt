package com.wespot.post.adapter

import com.wespot.post.Post
import com.wespot.post.PostEntity
import com.wespot.post.PostImages
import com.wespot.post.mapper.PostCategoryMapper
import com.wespot.post.mapper.PostImageMapper
import com.wespot.post.mapper.PostMapper
import com.wespot.post.port.out.PostPort
import com.wespot.post.repository.PostCategoryJpaRepository
import com.wespot.post.repository.PostImageJpaRepository
import com.wespot.post.repository.PostJpaRepository
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Repository

@Repository
class PostAdapter(
    private val postJpaRepository: PostJpaRepository,
    private val postImageJpaRepository: PostImageJpaRepository,
    private val userPort: UserPort,
    private val postCategoryJpaRepository: PostCategoryJpaRepository
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

    override fun searchByTitle(title: String): List<Post> {
        val posts = postJpaRepository.findAllByTitleContaining(title = title)

        return getCompletePost(posts)
    }

    private fun getCompletePost(postEntities: List<PostEntity>): List<Post> {
        val userIds = postEntities.map { it.userId }.distinct()
        val categoryIds = postEntities.map { it.categoryId }.distinct()
        val postIds = postEntities.map { it.id }.distinct()

        val userIdToUser = userPort.findAllByIdIn(userIds)
            .associateBy { it.id }
        val categoryIdToCategory = postCategoryJpaRepository.findAllByIdIn(categoryIds)
            .map { PostCategoryMapper.toDomain(it) }
            .associateBy { it.id }
        val postIdToPostImages = postImageJpaRepository.findAllByPostIdIn(postIds)
            .map { PostImageMapper.toDomain(it) }
            .groupBy { it.postId }

        return postEntities.map {
            PostMapper.toDomain(
                entity = it,
                postCategory = categoryIdToCategory[it.categoryId]!!,
                user = userIdToUser[it.userId]!!,
                postImages = postIdToPostImages[it.id]?.let { postImages -> PostImages(postImages) }
            )
        }
    }

    override fun searchByDescription(description: String): List<Post> {
        val posts = postJpaRepository.findAllByDescriptionContaining(description = description)

        return getCompletePost(posts)
    }

}
