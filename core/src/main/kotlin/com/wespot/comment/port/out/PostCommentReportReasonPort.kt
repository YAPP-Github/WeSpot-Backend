package com.wespot.comment.port.out

import com.wespot.comment.PostCommentReportReason

interface PostCommentReportReasonPort {

    fun saveAll(
        postCommentReportId: Long,
        postCommentReportReasons: List<PostCommentReportReason>
    ): List<PostCommentReportReason>

    fun findAllByPostCommentReportId(postCommentReportId: Long): List<PostCommentReportReason>

}
