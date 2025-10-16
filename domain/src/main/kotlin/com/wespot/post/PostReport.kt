package com.wespot.post

import com.wespot.exception.CustomException
import java.time.LocalDateTime

class PostReport(
    val id: Long = 0L,
    val postId: Long,
    val userId: Long,
    val postReportReasons: List<PostReportReason> = mutableListOf(),
    val createdAt: LocalDateTime = LocalDateTime.now()
) {

    fun addReportReasons(postReportReasons: List<PostReportReason>) {
        require(postReportReasons.isNotEmpty()) {
            throw CustomException(message = "신고 사유는 최소 1개 이상이어야 합니다.")
        }

        postReportReasons.forEach {
            this.postReportReasons.plus(it)
        }
    }

}
