package com.wespot.post.port.`in`

import com.wespot.post.dto.response.PostCategoryDetailResponses
import com.wespot.post.dto.response.PostCategoryResponse

interface PostCategoryUseCase {

    fun getCategoriesDetail(): List<PostCategoryDetailResponses>

    fun getCategories(): List<PostCategoryResponse>

}
