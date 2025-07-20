package com.wespot.post

import com.wespot.post.port.`in`.PostBlockUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/post")
class PostBlockController(
    private val postBlockUseCase: PostBlockUseCase
) {

    @PostMapping("/{postId}/block")
    fun blockPost(@PathVariable postId: Long): ResponseEntity<Unit> {
        postBlockUseCase.blockPost(postId)

        return ResponseEntity.noContent().build()
    }

}
