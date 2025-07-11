package com.wespot.post.port.`in`

import com.wespot.post.dto.response.PostResponse

interface PostInquiryByCategoryUseCase {

    fun findPostsByCategoryId(categoryId: Long): List<PostResponse>

    fun findPostsByMajorCategoryName(majorCategoryName: String): List<PostResponse>

    fun findPostById(postId: Long): PostResponse

    fun findCommentedPosts(): List<PostResponse>

    fun findScrappedPosts(): List<PostResponse>

    fun findWrittenPosts(): List<PostResponse>

}
