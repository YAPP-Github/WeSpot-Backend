package com.wespot.post

import com.wespot.post.dto.response.PostResponse
import com.wespot.post.port.`in`.PostInquiryByCategoryUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/post")
class PostInquiryController(
    private val postInquiryByCategoryUseCase: PostInquiryByCategoryUseCase
) {

    @GetMapping("/details")
    fun findPostsByCategoryId(categoryId: Long): ResponseEntity<List<PostResponse>> {
        val responses = postInquiryByCategoryUseCase.findPostsByCategoryId(categoryId)

        return ResponseEntity.ok(responses)
    }

    @GetMapping
    fun findPostsByMajorCategoryName(majorCategoryName: String): ResponseEntity<List<PostResponse>> {
        val responses = postInquiryByCategoryUseCase.findPostsByMajorCategoryName(majorCategoryName)

        return ResponseEntity.ok(responses)
    }

}
