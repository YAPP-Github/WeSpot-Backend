package com.wespot.post.service

import com.wespot.exception.CustomException
import com.wespot.post.dto.response.PostResponse
import com.wespot.post.port.`in`.PostInquiryByCategoryUseCase
import com.wespot.post.port.out.PostCategoryPort
import com.wespot.post.port.out.PostPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostInquiryByCategoryService(
    private val postCategoryPort: PostCategoryPort,
    private val postPort: PostPort,
) : PostInquiryByCategoryUseCase {

    @Transactional(readOnly = false)
    override fun findPostsByCategoryId(categoryId: Long): List<PostResponse> {
        val postCategory = postCategoryPort.findById(categoryId) ?: throw CustomException(
            status = HttpStatus.BAD_REQUEST,
            message = "존재하지 않는 카테고리입니다."
        )

        return postPort.findAllByCategoryId(categoryId = postCategory.id)
            .map { PostResponse.from(it) }
    }

    @Transactional(readOnly = false)
    override fun findPostsByMajorCategoryName(majorCategoryName: String): List<PostResponse> {
        val categoryIds = postCategoryPort.findAllByMajorCategoryName(majorCategoryName)
            .map { it.id }

        return postPort.findAllByCategoryIdIn(categoryIds = categoryIds)
            .map { PostResponse.from(it) }
    }


}
