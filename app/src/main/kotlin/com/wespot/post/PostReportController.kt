package com.wespot.post

import com.wespot.post.dto.request.PostReportRequest
import com.wespot.post.port.`in`.PostReportUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/post")
class PostReportController(
    private val postReportUseCase: PostReportUseCase
) {

    @PostMapping("/{postId}/report")
    fun reportPost(
        @PathVariable postId: Long,
        @RequestBody(required = false) request: PostReportRequest?
    ): ResponseEntity<Unit> {
        postReportUseCase.reportPost(postId, request)

        return ResponseEntity.status(HttpStatus.CREATED)
            .build()
    }

}
