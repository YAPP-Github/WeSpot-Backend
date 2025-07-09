package com.wespot.comment.event

import com.wespot.comment.PostComment

data class PostCommentCreatedEvent(
    val postComment: PostComment
) {
}
