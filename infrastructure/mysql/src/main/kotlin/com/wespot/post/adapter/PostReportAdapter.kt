package com.wespot.post.adapter

import com.wespot.post.PostReport
import com.wespot.post.mapper.PostReportMapper
import com.wespot.post.port.out.PostReportPort
import com.wespot.post.repository.PostReportJpaRepository
import com.wespot.report.port.out.ReportReasonPort
import org.springframework.stereotype.Repository

@Repository
class PostReportAdapter(
    private val postReportJpaRepository: PostReportJpaRepository,
    private val reportReasonPort: ReportReasonPort,
) : PostReportPort {

    override fun findByPostIdAndUserId(postId: Long, userId: Long): PostReport? {
        return postReportJpaRepository.findByPostIdAndUserId(postId, userId)
            ?.let {
                PostReportMapper.toDomain(
                    it,
                    reportReason = reportReasonPort.findById(it.reportReasonId)
                        ?: throw IllegalStateException("신고 사유가 존재하지 않습니다.")
                )
            }
    }

    override fun deleteById(id: Long) {
        postReportJpaRepository.deleteById(id)
    }

    override fun save(postBlock: PostReport): PostReport {
        val postBlockEntity = PostReportMapper.toEntity(postBlock)
        val savedPostBlockEntity = postReportJpaRepository.save(postBlockEntity)
        return PostReportMapper.toDomain(
            savedPostBlockEntity,
            reportReason = postBlock.reportReason
        )
    }

    override fun deleteByPostId(postId: Long) {
        postReportJpaRepository.deleteByPostId(postId)
    }

}
