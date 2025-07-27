package com.wespot.post.port.`in`

import com.wespot.post.dto.response.FilterChipResponse
import com.wespot.post.dto.response.PostCategoryItemsResponse

interface PostCategoryUseCase {

    fun getCategoriesDetail(): List<PostCategoryItemsResponse>

    fun getCategories(): List<FilterChipResponse>

}
