package com.wespot.comment.event

import com.wespot.comment.PostComment
import com.wespot.user.User

data class PostCommentBlockEvent(
    val sender: User,
    val receiver: User,
    val postComment: PostComment
) {
}
