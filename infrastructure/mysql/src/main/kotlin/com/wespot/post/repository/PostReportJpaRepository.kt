package com.wespot.post.repository

import com.wespot.post.PostReportEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostReportJpaRepository : JpaRepository<PostReportEntity, Long> {
}
