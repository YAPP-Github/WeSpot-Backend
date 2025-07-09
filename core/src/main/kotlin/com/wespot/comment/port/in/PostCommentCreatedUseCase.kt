package com.wespot.comment.port.`in`

import com.wespot.comment.dto.PostCommentCreatedRequest

interface PostCommentCreatedUseCase {

    fun createComment(postCommentCreatedRequest: PostCommentCreatedRequest): Long

}
