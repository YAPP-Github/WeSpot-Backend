package com.wespot.post

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import org.springframework.http.HttpStatus

data class PostCategories(
    val eachMajorCategories: List<EachMajorCategory>
) {

    companion object {

        fun from(postCategories: List<PostCategory>): PostCategories {
            val groupedCategories = postCategories.groupBy { it.majorCategoryName }
                .filter { (_, categories) -> categories.isNotEmpty() }
                .map { (majorCategoryName, categories) ->
                    EachMajorCategory.of(
                        majorCategoryName = majorCategoryName,
                        postCategories = categories
                    )
                }

            return PostCategories(eachMajorCategories = groupedCategories)
        }
    }

    data class EachMajorCategory(
        val postCategories: List<PostCategory>
    ) {

        fun majorCategoryName(): String {
            return postCategories.firstOrNull()?.majorCategoryName
                ?: throw CustomException(
                    status = HttpStatus.INTERNAL_SERVER_ERROR,
                    view = ExceptionView.TOAST,
                    message = "대분류 카테고리 이름을 찾을 수 없습니다."
                )
        }


        companion object {
            fun of(majorCategoryName: String, postCategories: List<PostCategory>): EachMajorCategory {
                val isThereDifferenceCategoryName = postCategories.any { it.majorCategoryName != majorCategoryName }

                if (isThereDifferenceCategoryName) {
                    throw CustomException(
                        status = HttpStatus.INTERNAL_SERVER_ERROR,
                        view = ExceptionView.TOAST,
                        message = "하나의 카테고리 그룹에는 하나의 대분류만이 존재할 수 있습니다."
                    )
                }

                return EachMajorCategory(postCategories = postCategories)
            }
        }
    }

}
