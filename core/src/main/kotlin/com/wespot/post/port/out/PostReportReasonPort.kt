package com.wespot.post.port.out

import com.wespot.post.PostReportReason

interface PostReportReasonPort {

    fun saveAll(postReportId: Long, postReportReasons: List<PostReportReason>): List<PostReportReason>

    fun findByPostReportId(postReportId: Long): List<PostReportReason>

}
