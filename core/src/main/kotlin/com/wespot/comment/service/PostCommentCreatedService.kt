package com.wespot.comment.service

import com.wespot.EventUtils
import com.wespot.auth.service.SecurityUtils
import com.wespot.comment.PostComment
import com.wespot.comment.dto.PostCommentCreatedRequest
import com.wespot.comment.event.PostCommentCreatedEvent
import com.wespot.comment.port.`in`.PostCommentCreatedUseCase
import com.wespot.comment.port.out.PostCommentPort
import com.wespot.comment.port.out.PostValidatePort
import com.wespot.exception.CustomException
import com.wespot.lock.ExecutorWithLock
import com.wespot.lock.LockKey
import com.wespot.user.port.out.UserPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostCommentCreatedService(
    private val userPort: UserPort,
    private val postCommentPort: PostCommentPort,
    private val postValidatePort: PostValidatePort,
    private val executorWithLock: ExecutorWithLock,
) : PostCommentCreatedUseCase {

    @Transactional
    override fun createComment(postCommentCreatedRequest: PostCommentCreatedRequest): Long {
        return executorWithLock.execute(
            task = {
                if (!postValidatePort.existsPostById(postCommentCreatedRequest.postId)) {
                    throw CustomException(status = HttpStatus.BAD_REQUEST, message = "해당하는 게시글을 찾을 수 없습니다.")
                }

                val loginUser = SecurityUtils.getLoginUser(userPort = userPort)

                if (loginUser.canNotUseCommunity()) {
                    throw CustomException(status = HttpStatus.FORBIDDEN, message = "커뮤니티 이용이 제한된 사용자입니다.")
                }

                val postComment = PostComment.of(
                    postId = postCommentCreatedRequest.postId,
                    content = postCommentCreatedRequest.content,
                    user = loginUser
                )

                val savedPostComment = postCommentPort.save(postComment)
                EventUtils.publish(PostCommentCreatedEvent(savedPostComment))

                savedPostComment.id
            },
            lockKey = LockKey("postComment", "${postCommentCreatedRequest.postId}"),
        )
    }

}
