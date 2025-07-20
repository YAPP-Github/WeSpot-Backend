package com.wespot.post.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.exception.CustomException
import com.wespot.post.PostImage
import com.wespot.post.dto.request.UpdatedPostRequest
import com.wespot.post.port.`in`.PostEditUseCase
import com.wespot.post.port.out.PostCategoryPort
import com.wespot.post.port.out.PostPort
import com.wespot.user.port.out.UserPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostEditService(
    @Value("\${aws.cloud-front.url}")
    private val cloudFrontUrl: String,
    private val userPort: UserPort,
    private val postPort: PostPort,
    private val postCategoryPort: PostCategoryPort
) : PostEditUseCase {

    @Transactional
    override fun editPost(postId: Long, request: UpdatedPostRequest): Long {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val savedPost = postPort.findById(postId) ?: throw CustomException(message = "존재하지 않는 게시글입니다.")
        val category = postCategoryPort.findById(categoryId = request.categoryId)
            ?: throw CustomException(message = "존재하지 않는 카테고리입니다.")

        return savedPost.update(
            category = category,
            user = loginUser,
            title = request.title,
            description = request.description,
            images = request.imagesRequest?.map {
                PostImage.of(cloudFrontUrl = cloudFrontUrl, imageUrl = it.url, width = it.width, height = it.height)
            },
            toUpdatePost = { updatedPost -> postPort.save(updatedPost) }
        ).id
    }

}
