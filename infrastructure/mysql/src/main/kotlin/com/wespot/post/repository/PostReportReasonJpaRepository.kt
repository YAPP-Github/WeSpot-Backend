package com.wespot.post.repository

import com.wespot.post.PostReportReasonEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostReportReasonJpaRepository : JpaRepository<PostReportReasonEntity, Long> {

    fun findByPostReportId(postReportId: Long): List<PostReportReasonEntity>

    fun deleteByPostReportIdIn(postReportIds: List<Long>)

}
