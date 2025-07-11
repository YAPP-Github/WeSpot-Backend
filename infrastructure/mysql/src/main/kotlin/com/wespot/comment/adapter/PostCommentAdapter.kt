package com.wespot.comment.adapter

import com.wespot.comment.PostComment
import com.wespot.comment.PostCommentEntity
import com.wespot.comment.mapper.PostCommentMapper
import com.wespot.comment.port.out.PostCommentPort
import com.wespot.comment.repository.PostCommentJpaRepository
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Repository

@Repository
class PostCommentAdapter(
    private val userPort: UserPort,
    private val postCommentJpaRepository: PostCommentJpaRepository
) : PostCommentPort {

    override fun save(postComment: PostComment): PostComment {
        val postCommentEntity = PostCommentMapper.toEntity(postComment)
        val savedPostCommentEntity = postCommentJpaRepository.save(postCommentEntity)

        return PostCommentMapper.toDomain(savedPostCommentEntity, postComment.user)
    }

    override fun findAllByPostId(postId: Long): List<PostComment> {
        val postCommentEntities = postCommentJpaRepository.findAllByPostId(postId)

        return getCompletePostComments(postCommentEntities = postCommentEntities)
    }

    private fun getCompletePostComments(postCommentEntities: List<PostCommentEntity>): List<PostComment> {
        val userIds = postCommentEntities.map { it.userId }.distinct()

        val userIdToUser = userPort.findAllByIdIn(userIds)
            .associateBy { it.id }

        return postCommentEntities.map { PostCommentMapper.toDomain(it, userIdToUser[it.userId]!!) }
    }

}
