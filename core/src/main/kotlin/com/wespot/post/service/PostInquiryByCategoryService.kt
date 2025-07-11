package com.wespot.post.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.comment.port.out.PostCommentPort
import com.wespot.exception.CustomException
import com.wespot.post.dto.response.PostResponse
import com.wespot.post.port.`in`.PostInquiryByCategoryUseCase
import com.wespot.post.port.out.PostCategoryPort
import com.wespot.post.port.out.PostPort
import com.wespot.post.port.out.PostScrapPort
import com.wespot.user.port.out.UserPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostInquiryByCategoryService(
    private val userPort: UserPort,
    private val postCategoryPort: PostCategoryPort,
    private val postPort: PostPort,
    private val postCommentPort: PostCommentPort,
    private val postScrapPort: PostScrapPort
) : PostInquiryByCategoryUseCase {

    @Transactional(readOnly = false)
    override fun findPostsByCategoryId(categoryId: Long): List<PostResponse> {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val postCategory = postCategoryPort.findById(categoryId) ?: throw CustomException(
            status = HttpStatus.BAD_REQUEST,
            message = "존재하지 않는 카테고리입니다."
        )

        return postPort.findAllByCategoryId(categoryId = postCategory.id, viewerId = loginUser.id)
            .map { PostResponse.from(it) }
    }

    @Transactional(readOnly = false)
    override fun findPostsByMajorCategoryName(majorCategoryName: String): List<PostResponse> {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val categoryIds = postCategoryPort.findAllByMajorCategoryName(majorCategoryName)
            .map { it.id }

        return postPort.findAllByCategoryIdIn(categoryIds = categoryIds, viewerId = loginUser.id)
            .map { PostResponse.from(it) }
    }

    @Transactional(readOnly = false)
    override fun findPostById(postId: Long): PostResponse {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val post = postPort.findById(postId, viewerId = loginUser.id) ?: throw CustomException(
            status = HttpStatus.BAD_REQUEST,
            message = "존재하지 않는 게시글입니다."
        )

        return PostResponse.from(post)
    }

    @Transactional(readOnly = false)
    override fun findCommentedPosts(): List<PostResponse> {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val postIds = postCommentPort.findAllByUserId(userId = loginUser.id)
            .map { it.postId }
            .distinct()

        return postPort.findAllByPostIdIn(postIds = postIds, viewerId = loginUser.id)
            .map { PostResponse.from(it) }
    }

    @Transactional(readOnly = false)
    override fun findScrappedPosts(): List<PostResponse> {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val postIds = postScrapPort.findAllByUserId(userId = loginUser.id)
            .map { it.postId }
            .distinct()

        return postPort.findAllByPostIdIn(postIds = postIds, viewerId = loginUser.id)
            .map { PostResponse.from(it) }
    }

    @Transactional(readOnly = false)
    override fun findWrittenPosts(): List<PostResponse> {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)

        return postPort.findAllByUserId(authorId = loginUser.id).map { PostResponse.from(it) }
    }


}
