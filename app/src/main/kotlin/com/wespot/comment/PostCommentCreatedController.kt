package com.wespot.comment

import com.wespot.comment.dto.PostCommentCreatedRequest
import com.wespot.comment.port.`in`.PostCommentCreatedUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/post/comment")
class PostCommentCreatedController(
    private val postCommentCreatedUseCase: PostCommentCreatedUseCase
) {

    @PostMapping
    fun createPostComment(
        @RequestBody postCommentCreatedRequest: PostCommentCreatedRequest
    ): ResponseEntity<Long> {
        val commentId = postCommentCreatedUseCase.createComment(postCommentCreatedRequest)

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(commentId)
    }

}
