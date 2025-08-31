package com.wespot.comment

import com.wespot.comment.dto.PostCommentReportRequest
import com.wespot.comment.port.`in`.PostCommentReportUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/post/comment")
class PostCommentReportController(
    private val postCommentReportUseCase: PostCommentReportUseCase
) {

    @PostMapping("/{commentId}/report")
    fun reportPostComment(
        @PathVariable commentId: Long,
        @RequestBody(required = false) request: PostCommentReportRequest?
    ): ResponseEntity<Unit> {
        postCommentReportUseCase.reportComment(commentId, request)

        return ResponseEntity.status(HttpStatus.CREATED)
            .build()
    }

}
