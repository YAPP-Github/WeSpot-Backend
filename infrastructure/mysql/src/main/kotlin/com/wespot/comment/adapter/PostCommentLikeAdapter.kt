package com.wespot.comment.adapter

import com.wespot.comment.PostCommentLike
import com.wespot.comment.mapper.PostCommentLikeMapper
import com.wespot.comment.port.out.PostCommentLikePort
import com.wespot.comment.repository.PostCommentLikeJpaRepository
import org.springframework.stereotype.Repository

@Repository
class PostCommentLikeAdapter(
    private val postCommentLikeJpaRepository: PostCommentLikeJpaRepository
) : PostCommentLikePort {

    override fun isExistsByPostCommentIdAndUserId(postCommentId: Long, userId: Long): Boolean {
        return postCommentLikeJpaRepository.existsByPostCommentIdAndUserId(
            postCommentId = postCommentId,
            userId = userId
        )
    }

    override fun findAllByPostCommentIdInAndUserId(
        postCommentIds: List<Long>,
        userId: Long
    ): List<PostCommentLike> {
        return postCommentLikeJpaRepository.findAllByPostCommentIdInAndUserId(
            postCommentIds = postCommentIds,
            userId = userId
        ).map { PostCommentLikeMapper.toDomain(it) }
    }

    override fun findByPostCommentIdAndUserId(postCommentId: Long, userId: Long): PostCommentLike? {
        return postCommentLikeJpaRepository.findByPostCommentIdAndUserId(
            postCommentId = postCommentId,
            userId = userId
        )?.let { PostCommentLikeMapper.toDomain(it) }
    }

    override fun save(postCommentLike: PostCommentLike): PostCommentLike {
        val postCommentLikeEntity = PostCommentLikeMapper.toEntity(postCommentLike)
        val savedPostCommentLikeEntity = postCommentLikeJpaRepository.save(postCommentLikeEntity)

        return PostCommentLikeMapper.toDomain(savedPostCommentLikeEntity)
    }

    override fun deleteById(id: Long) {
        postCommentLikeJpaRepository.deleteById(id)
    }

    override fun deleteByPostCommentIdIn(postCommentIds: List<Long>) {
        postCommentLikeJpaRepository.deleteByPostCommentIdIn(postCommentIds)
    }

}
