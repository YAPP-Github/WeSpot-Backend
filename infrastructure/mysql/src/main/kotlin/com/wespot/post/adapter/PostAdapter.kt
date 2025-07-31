package com.wespot.post.adapter

import com.wespot.comment.port.out.PostValidatePort
import com.wespot.exception.CustomException
import com.wespot.post.Post
import com.wespot.post.PostEntity
import com.wespot.post.PostImages
import com.wespot.post.PostStatusByViewer
import com.wespot.post.mapper.PostCategoryMapper
import com.wespot.post.mapper.PostImageMapper
import com.wespot.post.mapper.PostMapper
import com.wespot.post.mapper.PostProfileMapper
import com.wespot.post.port.out.PostPort
import com.wespot.post.repository.*
import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PostAdapter(
    private val postJpaRepository: PostJpaRepository,
    private val postImageJpaRepository: PostImageJpaRepository,
    private val userPort: UserPort,
    private val postCategoryJpaRepository: PostCategoryJpaRepository,
    private val postLikeJpaRepository: PostLikeJpaRepository,
    private val postNotificationJpaRepository: PostNotificationJpaRepository,
    private val postScrapJpaRepository: PostScrapJpaRepository,
    private val postProfileJpaRepository: PostProfileJpaRepository,
) : PostPort, PostValidatePort {

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
            postImages = savedPostImages,
            postProfile = post.profile,
        )
    }

    override fun searchByTitleAndDescription(
        keyword: String,
        viewerId: Long?,
        inquirySize: Long,
        cursorId: Long?
    ): List<Post> {
        val posts = postJpaRepository.searchByTitleAndDescription(
            pattern = keyword,
            cursorId = cursorId ?: Long.MAX_VALUE,
            limit = inquirySize.toInt()
        )

        return getCompletePost(posts, viewerId)
    }

    override fun searchByTitle(title: String, viewerId: Long?): List<Post> {
        val posts = postJpaRepository.findByTitleContainingOrderByBaseEntityCreatedAtDesc(
            title = title,
        )

        return getCompletePost(posts, viewerId)
    }

    private fun getCompletePost(
        postEntities: List<PostEntity>,
        viewerId: Long?
    ): List<Post> {
        val viewer = viewerId?.let { userPort.findById(it) }
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
        val userIdToPostProfile = postProfileJpaRepository.findAllByUserIdIn(userIds)
            .map { PostProfileMapper.toDomain(it) }
            .associateBy { it.userId }

        val postIdToPostStatusByViewer = getPostIdToPostStatusByViewer(postIds, viewer = viewer)

        return postEntities.map { postEntity ->
            PostMapper.toDomain(
                entity = postEntity,
                postCategory = categoryIdToCategory[postEntity.categoryId]!!,
                user = userIdToUser[postEntity.userId]!!,
                postImages = postIdToPostImages[postEntity.id]?.let { postImages -> PostImages(postImages) },
                postStatusByViewer = postIdToPostStatusByViewer?.let { postIdToPostStatusByViewer[postEntity.id] },
                postProfile = userIdToPostProfile[postEntity.userId]!!
            )
        }
    }

    private fun getPostIdToPostStatusByViewer(postIds: List<Long>, viewer: User?): Map<Long, PostStatusByViewer>? {
        return viewer?.let { notNullViewer ->
            val postIdToPostLike = postLikeJpaRepository.findAllByPostIdInAndUserId(
                postIds = postIds, userId = notNullViewer.id
            ).associateBy { postLike -> postLike.postId }
            val postIdToPostNotification = postNotificationJpaRepository.findAllByPostIdInAndUserId(
                postIds = postIds, userId = notNullViewer.id
            ).associateBy { postNotification -> postNotification.postId }
            val postIdToPostScrap = postScrapJpaRepository.findAllByPostIdInAndUserId(
                postIds = postIds, userId = notNullViewer.id
            ).associateBy { postScrap -> postScrap.postId }

            return postIds.map { postId ->
                PostStatusByViewer(
                    postId = postId,
                    isViewerPushedLike = postIdToPostLike.containsKey(postId),
                    isViewerPushedNotification = postIdToPostNotification.containsKey(postId),
                    isViewerPushedScrap = postIdToPostScrap.containsKey(postId)
                )
            }.associateBy { postStatusByViewer -> postStatusByViewer.postId }
        }
    }

    override fun searchByDescription(
        description: String,
        viewerId: Long?,
    ): List<Post> {
        val posts = postJpaRepository.findAllByDescriptionContainingOrderByBaseEntityCreatedAtDesc(
            description = description,
        )

        return getCompletePost(posts, viewerId)
    }

    override fun findById(postId: Long, viewerId: Long?): Post? {
        return postJpaRepository.findByIdOrNull(postId)
            ?.let { getCompletePost(listOf(it), viewerId) }
            ?.first()
    }

    override fun findAllByCategoryId(
        categoryId: Long,
        viewerId: Long?,
        inquirySize: Long,
        cursorId: Long?
    ): List<Post> {
        val posts = postJpaRepository.findByCategoryIdAndIdLessThanOrderByBaseEntityCreatedAtDesc(
            categoryId = categoryId,
            cursorId = cursorId ?: Long.MAX_VALUE,
            pageable = PageRequest.of(0, inquirySize.toInt())
        )

        return getCompletePost(posts, viewerId)
    }

    override fun findAllByCategoryIdIn(
        categoryIds: List<Long>,
        viewerId: Long?, inquirySize: Long, cursorId: Long?
    ): List<Post> {
        val posts = postJpaRepository.findByCategoryIdInAndIdLessThanOrderByBaseEntityCreatedAtDesc(
            categoryIds = categoryIds,
            cursorId = cursorId ?: Long.MAX_VALUE,
            pageable = PageRequest.of(0, inquirySize.toInt())
        )

        return getCompletePost(posts, viewerId)
    }

    override fun findAllByUserId(authorId: Long, inquirySize: Long, cursorId: Long?): List<Post> {
        val posts = postJpaRepository.findAllByUserIdAndIdLessThanOrderByBaseEntityCreatedAtDesc(
            userId = authorId,
            cursorId = cursorId ?: Long.MAX_VALUE,
            pageable = PageRequest.of(0, inquirySize.toInt())
        )

        return getCompletePost(posts, authorId)
    }

    override fun findAllByPostIdIn(
        postIds: List<Long>,
        viewerId: Long?,
        inquirySize: Long,
        cursorId: Long?
    ): List<Post> {
        val posts = postJpaRepository.findAllByIdInAndIdLessThanOrderByBaseEntityCreatedAtDesc(
            postIds = postIds,
            cursorId = cursorId ?: Long.MAX_VALUE,
            pageable = PageRequest.of(0, inquirySize.toInt())
        )

        return getCompletePost(posts, viewerId)
    }

    override fun findAllRecentPost(
        viewerId: Long?,
        inquirySize: Long,
        cursorId: Long?,
    ): List<Post> {
        val findAllByOrderByBaseEntityCreatedAtDesc =
            postJpaRepository.findAllByIdLessThanOrderByBaseEntityCreatedAtDesc(
                cursorId = cursorId ?: Long.MAX_VALUE,
                pageable = PageRequest.of(0, inquirySize.toInt())
            )

        return getCompletePost(findAllByOrderByBaseEntityCreatedAtDesc, viewerId)
    }

    override fun deleteById(id: Long) {
        postJpaRepository.deleteById(id)
    }

    override fun existsPostById(postId: Long): Boolean {
        return postJpaRepository.existsById(postId)
    }

}
