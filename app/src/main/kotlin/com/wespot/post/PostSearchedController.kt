package com.wespot.post

import com.wespot.post.dto.response.PostComponentResponse
import com.wespot.post.port.`in`.PostSearchedUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/post")
class PostSearchedController(
    private val postSearchedUseCase: PostSearchedUseCase
) {

    @GetMapping("/search")
    fun searchPost(
        @RequestParam keyword: String,
    ): ResponseEntity<List<PostComponentResponse>> {
        val response = postSearchedUseCase.search(keyword = keyword)

        return ResponseEntity.ok(response)
    }

}
