package com.wespot.post

import com.wespot.post.dto.request.CreatedPostRequest
import com.wespot.post.port.`in`.PostCreatedUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/post")
class PostCreatedController(
    private val postCreatedUseCase: PostCreatedUseCase
) {

    @PostMapping
    fun createPost(
        @RequestBody createdPostRequest: CreatedPostRequest
    ): ResponseEntity<Unit> {
        postCreatedUseCase.createPost(createdPostRequest)

        return ResponseEntity.status(HttpStatus.CREATED)
            .build()
    }

}
