package com.wespot.comment

import com.wespot.comment.port.`in`.PostCommentReportUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/post/comment")
class PostCommentReportController(
    private val postCommentReportUseCase: PostCommentReportUseCase
) {

    @PostMapping("/{commentId}/report")
    fun reportPostComment(@PathVariable commentId: Long): ResponseEntity<Unit> {
        postCommentReportUseCase.reportComment(commentId)

        return ResponseEntity.status(HttpStatus.CREATED)
            .build()
    }

}
