package com.wespot.comment.adapter

import com.wespot.comment.PostCommentReport
import com.wespot.comment.mapper.PostCommentReportMapper
import com.wespot.comment.port.out.PostCommentReportPort
import com.wespot.comment.repository.PostCommentReportJpaRepository
import org.springframework.stereotype.Repository

@Repository
class PostCommentReportAdapter(
    private val postCommentReportJpaRepository: PostCommentReportJpaRepository,
) : PostCommentReportPort {

    override fun findByPostCommentIdAndUserId(postCommentId: Long, userId: Long): PostCommentReport? {
        return postCommentReportJpaRepository.findByPostCommentIdAndUserId(postCommentId, userId)
            ?.let { PostCommentReportMapper.toDomain(it) }
    }

    override fun deleteById(id: Long) {
        postCommentReportJpaRepository.deleteById(id)
    }

    override fun save(postCommentReport: PostCommentReport): PostCommentReport {
        val postCommentReportEntity = PostCommentReportMapper.toEntity(postCommentReport)
        val savedPostCommentReportEntity = postCommentReportJpaRepository.save(postCommentReportEntity)

        return PostCommentReportMapper.toDomain(savedPostCommentReportEntity)
    }

    override fun deleteByPostCommentIdIn(postCommentId: List<Long>) {
        postCommentReportJpaRepository.deleteByPostCommentIdIn(postCommentId)
    }

}
