package com.wespot.post.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.post.PostCategories

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PostCategoryResponse(
    val majorCategoryName: String,
) {

    companion object {

        private const val ALL_INCLUDE_CATEGORY_NAME = "전체"
        val ALL_INCLUDE_CATEGORY = PostCategoryResponse(majorCategoryName = ALL_INCLUDE_CATEGORY_NAME)

        fun from(eachMajorCategory: PostCategories.EachMajorCategory): PostCategoryResponse {
            return PostCategoryResponse(majorCategoryName = eachMajorCategory.majorCategoryName())
        }

        fun from(name: String): PostCategoryResponse {
            return PostCategoryResponse(majorCategoryName = name)
        }

    }

}
