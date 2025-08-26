package com.wespot.comment.service

import com.wespot.EventUtils
import com.wespot.auth.service.SecurityUtils
import com.wespot.comment.event.PostCommentDeleteEvent
import com.wespot.comment.port.`in`.PostCommentDeletedUseCase
import com.wespot.comment.port.out.PostCommentPort
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostCommentDeletedService(
    private val userPort: UserPort,
    private val postCommentPort: PostCommentPort
) : PostCommentDeletedUseCase {

    @Transactional
    override fun deleteComment(commentId: Long) {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val postComment = postCommentPort.findById(id = commentId) ?: throw IllegalArgumentException("존재하지 않는 댓글입니다.")

        if (!postComment.isAuthor(loginUser.id)) {
            throw IllegalArgumentException("댓글 작성자만 삭제할 수 있습니다.")
        }

        postCommentPort.deleteByCommentId(commentId)
        EventUtils.publish(PostCommentDeleteEvent(postComment))
    }

}
