package com.wespot.post.server_driven

import com.wespot.post.PostCategories
import com.wespot.post.PostCategory

class PostCategoryItems(
    val category: String,
    val chips: List<PostCategoryItem>
) {

    data class PostCategoryItem(
        val id: Long,
        val text: String,
    ) {

        companion object {
            fun from(postCategory: PostCategory): PostCategoryItem {
                return PostCategoryItem(
                    id = postCategory.id,
                    text = postCategory.name
                )
            }
        }

    }

    companion object {

        fun from(eachMajorCategory: PostCategories.EachMajorCategory): PostCategoryItems {
            return PostCategoryItems(
                category = eachMajorCategory.majorCategoryName(),
                chips = eachMajorCategory.postCategories
                    .map { PostCategoryItem.from(it) }
            )
        }

    }

}
