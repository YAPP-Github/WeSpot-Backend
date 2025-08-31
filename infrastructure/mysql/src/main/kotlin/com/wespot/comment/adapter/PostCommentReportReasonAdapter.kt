package com.wespot.comment.adapter

import com.wespot.comment.PostCommentReportReason
import com.wespot.comment.mapper.PostCommentReportReasonMapper
import com.wespot.comment.port.out.PostCommentReportReasonPort
import com.wespot.comment.repository.PostCommentReportReasonJpaRepository
import com.wespot.report.port.out.ReportReasonPort
import org.springframework.stereotype.Repository

@Repository
class PostCommentReportReasonAdapter(
    private val postCommentReportReasonJpaRepository: PostCommentReportReasonJpaRepository,
    private val reportReasonPort: ReportReasonPort,
) : PostCommentReportReasonPort {

    override fun saveAll(
        postCommentReportId: Long,
        postCommentReportReasons: List<PostCommentReportReason>
    ): List<PostCommentReportReason> {
        val postCommentReportReasonEntities =
            postCommentReportReasons.map { it.registeredInPostCommentReport(postCommentReportId) }
                .map { PostCommentReportReasonMapper.toEntity(it) }
        val reportReasonIdToReportReason =
            postCommentReportReasons
                .map { it.reportReasonWithCustomReason.reportReason }
                .associateBy { it.id }

        return postCommentReportReasonJpaRepository.saveAll(postCommentReportReasonEntities)
            .map {
                PostCommentReportReasonMapper.toDomain(
                    it,
                    reportReason = reportReasonIdToReportReason[it.reportReasonId]!!
                )
            }
    }

    override fun findAllByPostCommentReportId(postCommentReportId: Long): List<PostCommentReportReason> {
        val postCommentReportReasonEntities =
            postCommentReportReasonJpaRepository.findByPostCommentReportId(postCommentReportId)
        val reportReasonIds = postCommentReportReasonEntities.map { it.reportReasonId }
        val reportReasonIdToReportReason = reportReasonPort.findAllByIdIn(reportReasonIds)
            .associateBy { it.id }

        return postCommentReportReasonEntities.map {
            PostCommentReportReasonMapper.toDomain(
                it,
                reportReason = reportReasonIdToReportReason[it.reportReasonId]!!
            )
        }
    }
}
