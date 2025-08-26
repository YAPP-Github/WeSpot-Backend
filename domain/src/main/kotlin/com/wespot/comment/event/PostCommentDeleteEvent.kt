package com.wespot.comment.event

import com.wespot.comment.PostComment

data class PostCommentDeleteEvent(
    val postComment: PostComment
) {

}
