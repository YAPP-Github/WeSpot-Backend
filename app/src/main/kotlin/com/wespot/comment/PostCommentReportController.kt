package com.wespot.comment

import com.wespot.comment.port.`in`.PostCommentReportUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/post/comment")
class PostCommentReportController(
    private val postCommentReportUseCase: PostCommentReportUseCase
) {

    @PatchMapping("/{commentId}/report")
    fun likePostComment(@PathVariable commentId: Long): ResponseEntity<Unit> {
        postCommentReportUseCase.reportComment(commentId)

        return ResponseEntity.noContent()
            .build()
    }

}
