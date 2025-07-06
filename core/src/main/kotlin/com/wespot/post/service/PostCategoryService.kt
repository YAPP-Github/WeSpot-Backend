package com.wespot.post.service

import com.wespot.post.PostCategories
import com.wespot.post.dto.response.PostCategoryDetailResponses
import com.wespot.post.dto.response.PostCategoryResponse
import com.wespot.post.port.`in`.PostCategoryUseCase
import com.wespot.post.port.out.PostCategoryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostCategoryService(
    private val postCategoryPort: PostCategoryPort
) : PostCategoryUseCase {

    @Transactional(readOnly = true)
    override fun getCategoriesDetail(): List<PostCategoryDetailResponses> {
        val postCategories = PostCategories.from(postCategories = postCategoryPort.findAll())

        return postCategories.eachMajorCategories
            .map { PostCategoryDetailResponses.from(it) }
    }

    @Transactional(readOnly = true)
    override fun getCategories(): List<PostCategoryResponse> {
        val postCategories = PostCategories.from(postCategories = postCategoryPort.findAll())
        val eachMajorCategories = postCategories.eachMajorCategories
            .map { PostCategoryResponse.from(it) }

        return listOf(PostCategoryResponse.ALL_INCLUDE_CATEGORY) + eachMajorCategories
    }

}
