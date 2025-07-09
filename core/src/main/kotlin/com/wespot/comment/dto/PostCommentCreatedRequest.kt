package com.wespot.comment.dto

data class PostCommentCreatedRequest(
    val postId: Long,
    val content: String,
) {

}
