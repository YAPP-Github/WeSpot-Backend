package com.wespot.comment.adapter

import com.wespot.comment.PostCommentReport
import com.wespot.comment.mapper.PostCommentReportMapper
import com.wespot.comment.port.out.PostCommentReportPort
import com.wespot.comment.port.out.PostCommentReportReasonPort
import com.wespot.comment.repository.PostCommentReportJpaRepository
import com.wespot.exception.CustomException
import com.wespot.post.port.out.PostReportReasonPort
import com.wespot.report.port.out.ReportReasonPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository

@Repository
class PostCommentReportAdapter(
    private val postCommentReportJpaRepository: PostCommentReportJpaRepository,
    private val postCommentReportReasonPort: PostCommentReportReasonPort,
) : PostCommentReportPort {

    override fun findByPostCommentIdAndUserId(postCommentId: Long, userId: Long): PostCommentReport? {
        return postCommentReportJpaRepository.findByPostCommentIdAndUserId(postCommentId, userId)
            ?.let {
                PostCommentReportMapper.toDomain(
                    it,
                    postCommentReportReasons = postCommentReportReasonPort.findAllByPostCommentReportId(it.id),
                )
            }
    }

    override fun deleteById(id: Long) {
        postCommentReportJpaRepository.deleteById(id)
    }

    override fun save(postCommentReport: PostCommentReport): PostCommentReport {
        val postCommentReportEntity = PostCommentReportMapper.toEntity(postCommentReport)
        val savedPostCommentReportEntity = postCommentReportJpaRepository.save(postCommentReportEntity)
        val postCommentReportReasons = postCommentReportReasonPort.saveAll(
            postCommentReportId = postCommentReport.id,
            postCommentReportReasons = postCommentReport.postCommentReportReasons
        )

        return PostCommentReportMapper.toDomain(
            entity = savedPostCommentReportEntity,
            postCommentReportReasons = postCommentReportReasons
        )
    }

    override fun deleteByPostCommentIdIn(postCommentId: List<Long>) {
        postCommentReportJpaRepository.deleteByPostCommentIdIn(postCommentId)
    }

}
