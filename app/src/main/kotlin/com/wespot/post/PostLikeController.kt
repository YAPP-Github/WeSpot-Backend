package com.wespot.post

import com.wespot.post.port.`in`.PostLikeUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/post")
class PostLikeController(
    private val postLikeUseCase: PostLikeUseCase
) {

    @PostMapping("/{postId}/like")
    fun likePost(@PathVariable postId: Long): ResponseEntity<Unit> {
        postLikeUseCase.likePost(postId)

        return ResponseEntity.noContent()
            .build()
    }

}
