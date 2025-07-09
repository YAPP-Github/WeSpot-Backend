package com.wespot.comment.service

import com.wespot.EventUtils
import com.wespot.auth.service.SecurityUtils
import com.wespot.comment.PostComment
import com.wespot.comment.dto.PostCommentCreatedRequest
import com.wespot.comment.event.PostCommentCreatedEvent
import com.wespot.comment.port.`in`.PostCommentCreatedUseCase
import com.wespot.comment.port.out.PostCommentPort
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostCommentCreatedService(
    private val userPort: UserPort,
    private val postCommentPort: PostCommentPort
) : PostCommentCreatedUseCase {

    @Transactional
    override fun createComment(postCommentCreatedRequest: PostCommentCreatedRequest): Long {
        // 알림 발생
        // 익명 프로필 생성
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)

        val postComment = PostComment.of(
            postId = postCommentCreatedRequest.postId,
            content = postCommentCreatedRequest.content,
            user = loginUser
        )

        val savedPostComment = postCommentPort.save(postComment)
        EventUtils.publish(PostCommentCreatedEvent(savedPostComment))

        return savedPostComment.id
    }

}
