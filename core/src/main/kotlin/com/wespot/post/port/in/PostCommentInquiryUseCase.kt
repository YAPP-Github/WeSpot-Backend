package com.wespot.post.port.`in`

import com.wespot.post.dto.response.PostCommentResponse

interface PostCommentInquiryUseCase {

    fun findAllByPostId(postId: Long): List<PostCommentResponse>

}
