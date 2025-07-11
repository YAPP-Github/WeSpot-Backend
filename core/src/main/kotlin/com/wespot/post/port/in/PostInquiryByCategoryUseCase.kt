package com.wespot.post.port.`in`

import com.wespot.post.dto.response.PostResponse

interface PostInquiryByCategoryUseCase {

    fun findPostsByCategoryId(categoryId: Long): List<PostResponse>

    fun findPostsByMajorCategoryName(majorCategoryName: String): List<PostResponse>

}
