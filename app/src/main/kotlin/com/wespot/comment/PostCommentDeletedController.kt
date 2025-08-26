package com.wespot.comment

import com.wespot.comment.port.`in`.PostCommentDeletedUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/post/comment")
class PostCommentDeletedController(
    private val postCommentDeletedUseCase: PostCommentDeletedUseCase
) {

    @DeleteMapping("/{commentId}")
    fun deletePostComment(
        @PathVariable commentId: Long
    ): ResponseEntity<Void> {
        postCommentDeletedUseCase.deleteComment(commentId)

        return ResponseEntity.noContent()
            .build()
    }


}
