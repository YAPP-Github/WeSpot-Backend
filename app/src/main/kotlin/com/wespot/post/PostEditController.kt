package com.wespot.post

import com.wespot.post.dto.request.UpdatedPostRequest
import com.wespot.post.port.`in`.PostEditUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/post")
class PostEditController(
    private val postEditUseCase: PostEditUseCase
) {

    @PutMapping("/{postId}")
    fun editPost(
        @PathVariable postId: Long,
        @RequestBody updatedPostRequest: UpdatedPostRequest
    ): ResponseEntity<Unit> {
        postEditUseCase.editPost(postId = postId, request = updatedPostRequest)

        return ResponseEntity.noContent()
            .build()
    }

}
