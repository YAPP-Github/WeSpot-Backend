package com.wespot.post

import com.wespot.post.port.`in`.PostScrapUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/post")
class PostScrapController(
    private val postScrapUseCase: PostScrapUseCase
) {

    @PostMapping("/{postId}/scrap")
    fun scrapPost(@PathVariable postId: Long): ResponseEntity<Unit> {
        postScrapUseCase.scrapPost(postId)

        return ResponseEntity.noContent()
            .build()
    }

}
