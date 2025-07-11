package com.wespot.comment.adapter

import com.wespot.comment.PostComment
import com.wespot.comment.PostCommentEntity
import com.wespot.comment.PostCommentStatusByViewer
import com.wespot.comment.mapper.PostCommentMapper
import com.wespot.comment.port.out.PostCommentPort
import com.wespot.comment.repository.PostCommentJpaRepository
import com.wespot.comment.repository.PostCommentLikeJpaRepository
import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PostCommentAdapter(
    private val userPort: UserPort,
    private val postCommentJpaRepository: PostCommentJpaRepository,
    private val postCommentLikeJpaRepository: PostCommentLikeJpaRepository,
) : PostCommentPort {

    override fun save(postComment: PostComment): PostComment {
        val postCommentEntity = PostCommentMapper.toEntity(postComment)
        val savedPostCommentEntity = postCommentJpaRepository.save(postCommentEntity)

        return PostCommentMapper.toDomain(savedPostCommentEntity, postComment.user)
    }

    override fun findAllByPostId(postId: Long, viewerId: Long?): List<PostComment> {
        val postCommentEntities = postCommentJpaRepository.findAllByPostId(postId)

        return getCompletePostComments(postCommentEntities = postCommentEntities, viewerId)
    }

    override fun findAllByUserId(userId: Long, viewerId: Long?): List<PostComment> {
        val postCommentEntities = postCommentJpaRepository.findAllByUserId(userId)

        return getCompletePostComments(postCommentEntities = postCommentEntities, viewerId = viewerId)
    }

    override fun findById(id: Long, viewerId: Long?): PostComment? {
        return postCommentJpaRepository.findByIdOrNull(id)
            ?.let { getCompletePostComments(listOf(it), viewerId = viewerId) }
            ?.first()
    }

    private fun getCompletePostComments(
        postCommentEntities: List<PostCommentEntity>,
        viewerId: Long?
    ): List<PostComment> {
        val viewer = viewerId?.let { userPort.findById(it) }
        val userIds = postCommentEntities.map { it.userId }.distinct()

        val userIdToUser = userPort.findAllByIdIn(userIds)
            .associateBy { it.id }

        val postCommentIds = postCommentEntities.map { it.id }.distinct()
        val postCommentIdToPostCommentStatusByViewer =
            getPostCommentIdToPostCommentStatusByViewer(postCommentIds, viewer)

        return postCommentEntities.map { postCommentEntity ->
            PostCommentMapper.toDomain(
                postCommentEntity,
                userIdToUser[postCommentEntity.userId]!!,
                postCommentStatusByViewer = postCommentIdToPostCommentStatusByViewer?.let { postCommentIdToPostCommentStatusByViewer[postCommentEntity.id] }
            )
        }
    }

    private fun getPostCommentIdToPostCommentStatusByViewer(
        postCommentIds: List<Long>,
        viewer: User?
    ): Map<Long, PostCommentStatusByViewer>? {
        return viewer?.let { notNullViewer ->
            val postCommentIdToLike = postCommentLikeJpaRepository.findAllByPostCommentIdInAndUserId(
                postCommentIds = postCommentIds,
                userId = notNullViewer.id
            ).associateBy { it.postCommentId }

            return postCommentIds.map {
                PostCommentStatusByViewer(
                    postCommentId = it,
                    isViewerPushedLike = postCommentIdToLike.contains(it)
                )
            }.associateBy { it.postCommentId }
        }
    }

}
