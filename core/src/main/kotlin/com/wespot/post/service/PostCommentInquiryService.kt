package com.wespot.post.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.post.dto.response.PostCommentResponse
import com.wespot.post.port.`in`.PostCommentInquiryUseCase
import com.wespot.comment.port.out.PostCommentLikePort
import com.wespot.comment.port.out.PostCommentPort
import com.wespot.exception.CustomException
import com.wespot.post.port.out.PostPort
import com.wespot.post.port.out.PostProfilePort
import com.wespot.user.port.out.UserPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostCommentInquiryService(
    private val userPort: UserPort,
    private val postCommentPort: PostCommentPort,
    private val postProfilePort: PostProfilePort,
    private val postPort: PostPort,
) : PostCommentInquiryUseCase {

    @Transactional(readOnly = true)
    override fun findAllByPostId(postId: Long): List<PostCommentResponse> {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val post = postPort.findById(postId) ?: throw CustomException(
            status = HttpStatus.BAD_REQUEST,
            message = "존재하지 않는 게시글입니다."
        )
        val postComments = postCommentPort.findAllByPostId(postId, viewerId = loginUser.id)
        val userIds = postComments.map { it.user.id }
        val userIdToProfile = postProfilePort.findByUserIdIn(userIds)
            .associateBy { it.userId }

        return postComments.map {
            PostCommentResponse.of(
                isPostOwner = post.isAuthor(loginUser.id),
                postComment = it,
                postProfile = userIdToProfile[it.user.id]!!
            )
        }
    }

}
