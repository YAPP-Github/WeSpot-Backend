package com.wespot.post.port.`in`

import com.wespot.common.dto.PostPagingResponse
import com.wespot.post.dto.response.PostComponentResponse

interface PostInquiryUseCase {

    fun findPostsByCategoryId(
        categoryId: Long,
        inquirySize: Long,
        cursorId: Long?,
    ): PostPagingResponse

    fun findPostsByMajorCategoryName(
        majorCategoryName: String,
        countOfPostsViewed: Long,
        inquirySize: Long,
        cursorId: Long?,
    ): PostPagingResponse

    fun findPostById(postId: Long): PostComponentResponse

    fun findCommentedPosts(
        inquirySize: Long,
        cursorId: Long?,
    ): PostPagingResponse

    fun findScrappedPosts(
        inquirySize: Long,
        cursorId: Long?,
    ): PostPagingResponse

    fun findWrittenPosts(
        inquirySize: Long,
        cursorId: Long?,
    ): PostPagingResponse

//    fun findAllPosts(
//        countOfPostsViewed: Long,
//        inquirySize: Long,
//        cursorId: Long?
//    ): PostPagingResponse

}
