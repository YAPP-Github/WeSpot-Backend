package com.wespot.post

import com.wespot.post.port.`in`.PostScrapUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/post")
class PostScrapController(
    private val postScrapUseCase: PostScrapUseCase
) {

    @PatchMapping("/{postId}/scrap")
    fun scrapPost(postId: Long): ResponseEntity<Unit> {
        postScrapUseCase.scrapPost(postId)

        return ResponseEntity.noContent()
            .build()
    }

}
