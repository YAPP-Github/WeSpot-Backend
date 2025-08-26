package com.wespot.post.service

import com.wespot.post.PostCategories
import com.wespot.post.dto.response.FilterChipResponse
import com.wespot.post.dto.response.PostCategoryItemsResponse
import com.wespot.post.port.`in`.PostCategoryUseCase
import com.wespot.post.port.out.PostCategoryPort
import com.wespot.post.server_driven.PostCategoryItems
import com.wespot.view.chip.FilterChip
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostCategoryService(
    private val postCategoryPort: PostCategoryPort
) : PostCategoryUseCase {

    @Transactional(readOnly = true)
    override fun getCategoriesDetail(): List<PostCategoryItemsResponse> {
        val postCategories = PostCategories.from(postCategories = postCategoryPort.findAll())

        return postCategories.eachMajorCategories
            .map { PostCategoryItems.from(it) }
            .map { PostCategoryItemsResponse.from(it) }
    }

    @Transactional(readOnly = true)
    override fun getCategories(): List<FilterChipResponse> {
        val ALL_INCLUDE_CATEGORY_NAME = "전체"
        val postCategories = PostCategories.from(postCategories = postCategoryPort.findAll())
        val eachMajorCategories = postCategories.eachMajorCategories
        var id = 1L

        val firstElement = FilterChip.of(id = id++, text = ALL_INCLUDE_CATEGORY_NAME)
        val otherElements = eachMajorCategories.map {
            FilterChip.of(
                id = id++,
                eachMajorCategory = it
            )
        }

        return (listOf(firstElement) + otherElements)
            .map { FilterChipResponse.from(it) }
    }

}
