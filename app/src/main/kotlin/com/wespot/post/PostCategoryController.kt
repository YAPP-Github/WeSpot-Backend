package com.wespot.post

import com.wespot.post.dto.response.FilterChipResponse
import com.wespot.post.dto.response.PostCategoryItemsResponse
import com.wespot.post.port.`in`.PostCategoryUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/category")
class PostCategoryController(
    private val postCategoryUseCase: PostCategoryUseCase
) {

    @GetMapping("/details")
    fun getPostCategoryDetailResponse(): ResponseEntity<List<PostCategoryItemsResponse>> {
        val response = postCategoryUseCase.getCategoriesDetail()

        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun getPostCategoryResponse(): ResponseEntity<List<FilterChipResponse>> {
        val response = postCategoryUseCase.getCategories()

        return ResponseEntity.ok(response)
    }

}
