package com.wespot.post

import com.wespot.post.dto.response.PostCommentResponse
import com.wespot.post.port.`in`.PostCommentInquiryUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/post/comment")
class PostCommentInquiryController(
    private val postCommentInquiryUseCase: PostCommentInquiryUseCase
) {

    @GetMapping
    fun findAllByPostId(postId: Long): ResponseEntity<List<PostCommentResponse>> {
        val responses = postCommentInquiryUseCase.findAllByPostId(postId)

        return ResponseEntity.ok(responses)
    }

}
