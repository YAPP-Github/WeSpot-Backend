package com.wespot.post

import com.wespot.common.dto.PostPagingResponse
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
        @RequestParam(required = false, defaultValue = "10") inquirySize: Long,
        @RequestParam(required = false) cursorId: Long? = null,
    ): ResponseEntity<PostPagingResponse> {
        val response = postSearchedUseCase.search(
            keyword = keyword,
            inquirySize = inquirySize,
            cursorId = cursorId
        )

        return ResponseEntity.ok(response)
    }

}
