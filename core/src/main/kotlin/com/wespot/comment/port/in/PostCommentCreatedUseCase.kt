package com.wespot.comment.port.`in`

interface PostCommentCreatedUseCase {

    fun createComment(commentCreatedRequest: CommentCreatedRequest): Long

}
