package com.wespot.post.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.post.PostCategories
import com.wespot.post.PostCategory

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PostCategoryDetailResponses(
    val majorCategoriesName: String,
    val categories: List<PostCategoryDetailResponse>
) {

    companion object {

        fun from(categories: PostCategories.EachMajorCategory): PostCategoryDetailResponses {
            return PostCategoryDetailResponses(
                majorCategoriesName = categories.majorCategoryName(),
                categories = categories.postCategories
                    .map { PostCategoryDetailResponse.from(it) }
            )
        }

    }

    data class PostCategoryDetailResponse(
        val id: Long,
        val name: String
    ) {

        companion object {

            fun from(postCategory: PostCategory): PostCategoryDetailResponse {
                return PostCategoryDetailResponse(
                    id = postCategory.id,
                    name = postCategory.name
                )
            }

        }

    }

}
