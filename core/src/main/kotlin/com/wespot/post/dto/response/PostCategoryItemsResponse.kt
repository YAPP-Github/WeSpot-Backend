package com.wespot.post.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.post.server_driven.PostCategoryItems

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PostCategoryItemsResponse(
    val category: String,
    val chips: List<PostCategoryItemResponse>
) {

    data class PostCategoryItemResponse(
        val id: Long,
        val text: String
    ) {

        companion object {
            fun from(postCategoryItem: PostCategoryItems.PostCategoryItem): PostCategoryItemResponse {
                return PostCategoryItemResponse(
                    id = postCategoryItem.id,
                    text = postCategoryItem.text
                )
            }
        }

    }

    companion object {
        fun from(postCategoryItems: PostCategoryItems): PostCategoryItemsResponse {
            return PostCategoryItemsResponse(
                category = postCategoryItems.category,
                chips = postCategoryItems.chips.map { PostCategoryItemResponse.from(it) }
            )
        }
    }

}
