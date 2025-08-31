package com.wespot.comment.port.`in`

import com.wespot.comment.dto.PostCommentReportRequest

interface PostCommentReportUseCase {

    fun reportComment(commentId: Long, request: PostCommentReportRequest?)

}
