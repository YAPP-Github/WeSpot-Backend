package com.wespot.post.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.post.Post
import com.wespot.post.dto.request.CreatedPostRequest
import com.wespot.post.port.`in`.PostCreatedUseCase
import com.wespot.post.port.out.PostCategoryPort
import com.wespot.post.port.out.PostPort
import com.wespot.user.port.out.UserPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostCreatedService(
    @Value("\${aws.cloud-front.url}")
    private val cloudFrontUrl: String,

    private val postPort: PostPort,
    private val postCategoryPort: PostCategoryPort,
    private val userPort: UserPort
) : PostCreatedUseCase {

    @Transactional
    override fun createPost(createdPostRequest: CreatedPostRequest): Long {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val category =
            postCategoryPort.findById(createdPostRequest.categoryId) ?: throw CustomException(
                status = HttpStatus.BAD_REQUEST,
                view = ExceptionView.TOAST,
                message = "존재하지 않는 카테고리입니다."
            )
        val post = Post.of(
            category = category,
            user = loginUser,
            title = createdPostRequest.title,
            description = createdPostRequest.description,
            images = createdPostRequest.urlOfImages,
            cloudFrontUrl = cloudFrontUrl,
            toSavePost = { toSavedPost -> postPort.save(toSavedPost) }
        )
        return post.id
    }

}
