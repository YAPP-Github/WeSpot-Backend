package com.wespot.comment

import com.wespot.comment.port.`in`.PostCommentLikeUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/post/comment")
class PostCommentLikeController(
    private val postCommentLikeUseCase: PostCommentLikeUseCase
) {

    @PostMapping("/{commentId}/like")
    fun likePostComment(@PathVariable commentId: Long): ResponseEntity<Unit> {
        postCommentLikeUseCase.likeComment(commentId)

        return ResponseEntity.noContent()
            .build()
    }

}
