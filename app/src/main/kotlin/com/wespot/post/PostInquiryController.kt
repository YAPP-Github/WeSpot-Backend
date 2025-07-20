package com.wespot.post

import com.wespot.post.dto.response.PostResponse
import com.wespot.post.port.`in`.PostInquiryByCategoryUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/post")
class PostInquiryController(
    private val postInquiryByCategoryUseCase: PostInquiryByCategoryUseCase
) {

    @GetMapping("/all")
    fun findAllPosts(): ResponseEntity<List<PostResponse>> {
        val responses = postInquiryByCategoryUseCase.findAllPosts()

        return ResponseEntity.ok(responses)
    }

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

    @GetMapping("/{postId}")
    fun findDetailPost(@PathVariable postId: Long): ResponseEntity<PostResponse> {
        val responses = postInquiryByCategoryUseCase.findPostById(postId)

        return ResponseEntity.ok(responses)
    }

    @GetMapping("/commented")
    fun findCommentedPosts(): ResponseEntity<List<PostResponse>> {
        val responses = postInquiryByCategoryUseCase.findCommentedPosts()

        return ResponseEntity.ok(responses)
    }

    @GetMapping("/scrapped")
    fun findScrappedPosts(): ResponseEntity<List<PostResponse>> {
        val responses = postInquiryByCategoryUseCase.findScrappedPosts()

        return ResponseEntity.ok(responses)
    }

    @GetMapping("/written")
    fun findWrittenPosts(): ResponseEntity<List<PostResponse>> {
        val responses = postInquiryByCategoryUseCase.findWrittenPosts()

        return ResponseEntity.ok(responses)
    }

}
