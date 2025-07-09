package com.wespot.comment.adapter

import com.wespot.comment.PostComment
import com.wespot.comment.mapper.PostCommentMapper
import com.wespot.comment.port.out.PostCommentPort
import com.wespot.comment.repository.PostCommentJpaRepository
import org.springframework.stereotype.Repository

@Repository
class PostCommentAdapter(
    private val postCommentJpaRepository: PostCommentJpaRepository
) : PostCommentPort {

    override fun save(postComment: PostComment): PostComment {
        val postCommentEntity = PostCommentMapper.toEntity(postComment)
        val savedPostCommentEntity = postCommentJpaRepository.save(postCommentEntity)

        return PostCommentMapper.toDomain(savedPostCommentEntity, postComment.user)
    }

}
