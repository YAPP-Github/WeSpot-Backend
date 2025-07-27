package com.wespot.post

import com.wespot.common.dto.PostPagingResponse
import com.wespot.post.dto.response.PostComponentResponse
import com.wespot.post.port.`in`.PostInquiryUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/post")
class PostInquiryController(
    private val postInquiryByCategoryUseCase: PostInquiryUseCase
) {

    @GetMapping("/all")
    fun findAllPosts(
        @RequestParam countOfPostsViewed: Long = 0L,
        @RequestParam(required = false, defaultValue = "10") inquirySize: Long,
        @RequestParam(required = false) cursorId: Long? = null,
    ): ResponseEntity<PostPagingResponse> {
        val responses = postInquiryByCategoryUseCase.findAllPosts(
            countOfPostsViewed,
            inquirySize,
            cursorId
        )

        return ResponseEntity.ok(responses)
    }

    @GetMapping("/details")
    fun findPostsByCategoryId(
        @RequestParam categoryId: Long,
        @RequestParam(required = false, defaultValue = "10") inquirySize: Long,
        @RequestParam(required = false) cursorId: Long? = null,
    ): ResponseEntity<PostPagingResponse> {
        val responses = postInquiryByCategoryUseCase.findPostsByCategoryId(
            categoryId,
            inquirySize = inquirySize,
            cursorId = cursorId
        )

        return ResponseEntity.ok(responses)
    }

    @GetMapping
    fun findPostsByMajorCategoryName(
        @RequestParam majorCategoryName: String,
        @RequestParam(required = false, defaultValue = "10") inquirySize: Long,
        @RequestParam(required = false) cursorId: Long? = null,
    ): ResponseEntity<PostPagingResponse> {
        val responses = postInquiryByCategoryUseCase.findPostsByMajorCategoryName(
            majorCategoryName,
            inquirySize = inquirySize,
            cursorId = cursorId
        )

        return ResponseEntity.ok(responses)
    }

    @GetMapping("/{postId}")
    fun findDetailPost(@PathVariable postId: Long): ResponseEntity<PostComponentResponse> {
        val responses = postInquiryByCategoryUseCase.findPostById(postId)

        return ResponseEntity.ok(responses)
    }

    @GetMapping("/commented")
    fun findCommentedPosts(
        @RequestParam(required = false, defaultValue = "10") inquirySize: Long,
        @RequestParam(required = false) cursorId: Long? = null,
    ): ResponseEntity<PostPagingResponse> {
        val responses = postInquiryByCategoryUseCase.findCommentedPosts(inquirySize = inquirySize, cursorId = cursorId)

        return ResponseEntity.ok(responses)
    }

    @GetMapping("/scrapped")
    fun findScrappedPosts(
        @RequestParam(required = false, defaultValue = "10") inquirySize: Long,
        @RequestParam(required = false) cursorId: Long? = null,
    ): ResponseEntity<PostPagingResponse> {
        val responses = postInquiryByCategoryUseCase.findScrappedPosts(inquirySize = inquirySize, cursorId = cursorId)

        return ResponseEntity.ok(responses)
    }

    @GetMapping("/written")
    fun findWrittenPosts(
        @RequestParam(required = false, defaultValue = "10") inquirySize: Long,
        @RequestParam(required = false) cursorId: Long? = null,
    ): ResponseEntity<PostPagingResponse> {
        val responses = postInquiryByCategoryUseCase.findWrittenPosts(inquirySize = inquirySize, cursorId = cursorId)

        return ResponseEntity.ok(responses)
    }

}
