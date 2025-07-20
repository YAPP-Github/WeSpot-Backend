package com.wespot.post

import com.wespot.post.port.`in`.PostDeleteUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/post")
class PostDeleteController(
    private val postDeleteUseCase: PostDeleteUseCase
) {

    @DeleteMapping("/{postId}")
    fun deletePost(
        @PathVariable postId: Long,
    ): ResponseEntity<Unit> {
        postDeleteUseCase.deletePost(postId)

        return ResponseEntity.noContent().build()
    }

}
