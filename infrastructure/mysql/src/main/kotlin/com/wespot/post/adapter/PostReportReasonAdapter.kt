package com.wespot.post.adapter

import com.wespot.post.PostReportReason
import com.wespot.post.mapper.PostReportReasonMapper
import com.wespot.post.port.out.PostReportReasonPort
import com.wespot.post.repository.PostReportReasonJpaRepository
import com.wespot.report.port.out.ReportReasonPort
import org.springframework.stereotype.Repository

@Repository
class PostReportReasonAdapter(
    private val postReportReasonJpaRepository: PostReportReasonJpaRepository,
    private val reportReasonPort: ReportReasonPort,
) : PostReportReasonPort {

    override fun saveAll(
        postReportId: Long,
        postReportReasons: List<PostReportReason>
    ): List<PostReportReason> {
        val postReportReasonEntities = postReportReasons.map { PostReportReasonMapper.toEntity(it) }
            .map { it.registeredInPostReport(postReportId = postReportId) }
        val reportReasonIdToReportReason =
            postReportReasons.map { it.reportReasonWithCustomReason.reportReason }
                .associateBy { it.id }

        return postReportReasonJpaRepository.saveAll(postReportReasonEntities)
            .map {
                PostReportReasonMapper.toDomain(
                    it,
                    reportReasonIdToReportReason[it.reportReasonId]!!
                )
            }
    }

    override fun findByPostReportId(postReportId: Long): List<PostReportReason> {
        val postReportReasonEntities =
            postReportReasonJpaRepository.findByPostReportId(postReportId = postReportId)
        val reportReasonIds = postReportReasonEntities.map { it.reportReasonId }
        val reportReasonIdToReportReason = reportReasonPort.findAllByIdIn(ids = reportReasonIds)
            .associateBy { it.id }

        return postReportReasonEntities.map {
            PostReportReasonMapper.toDomain(
                it,
                reportReason = reportReasonIdToReportReason[it.reportReasonId]!!
            )
        }
    }

}
