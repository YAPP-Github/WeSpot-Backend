package com.wespot.comment.port.out

import com.wespot.comment.PostCommentReport

interface PostCommentReportPort {

    fun findByPostCommentIdAndUserId(postCommentId: Long, userId: Long): PostCommentReport?

    fun deleteById(id: Long)

    fun save(postCommentReport: PostCommentReport): PostCommentReport

}
