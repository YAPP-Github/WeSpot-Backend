package com.wespot.comment.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.comment.PostCommentReport
import com.wespot.comment.port.`in`.PostCommentReportUseCase
import com.wespot.comment.port.out.PostCommentPort
import com.wespot.comment.port.out.PostCommentReportPort
import com.wespot.exception.CustomException
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostCommentReportService(
    private val userPort: UserPort,
    private val postCommentReportPort: PostCommentReportPort,
    private val postCommentPort: PostCommentPort
) : PostCommentReportUseCase {

    @Transactional
    override fun reportComment(commentId: Long) {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val postComment = postCommentPort.findById(commentId) ?: throw CustomException(message = "존재하지 않는 댓글입니다.")
        postCommentReportPort.findByPostCommentIdAndUserId(postCommentId = commentId, userId = loginUser.id)
            ?.let {
                postCommentReportPort.deleteById(it.id)
                val removedReportPostComment = postComment.removeReport()
                postCommentPort.save(removedReportPostComment)
            }
            ?: {
                val postCommentReport = PostCommentReport(postCommentId = commentId, userId = loginUser.id)
                postCommentReportPort.save(postCommentReport)
                val addedReportPostComment = postComment.addReport()
                postCommentPort.save(addedReportPostComment)
            }
    }

}
