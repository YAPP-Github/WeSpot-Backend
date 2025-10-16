package com.wespot.comment.event

import com.wespot.comment.PostComment
import com.wespot.user.User

data class PostCommentReportEvent(
    val sender: User,
    val receiver: User,
    val reason: String,
    val postComment: PostComment
) {
}
