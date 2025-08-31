package com.wespot.comment.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.comment.PostCommentReport
import com.wespot.comment.PostCommentReportReason
import com.wespot.comment.dto.PostCommentReportRequest
import com.wespot.comment.port.`in`.PostCommentReportUseCase
import com.wespot.comment.port.out.PostCommentPort
import com.wespot.comment.port.out.PostCommentReportPort
import com.wespot.exception.CustomException
import com.wespot.report.ReportReasonWithCustomReason
import com.wespot.report.port.out.ReportReasonPort
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostCommentReportService(
    private val userPort: UserPort,
    private val postCommentReportPort: PostCommentReportPort,
    private val postCommentPort: PostCommentPort,
    private val reportReasonPort: ReportReasonPort
) : PostCommentReportUseCase {

    @Transactional
    override fun reportComment(commentId: Long, request: PostCommentReportRequest?) {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val postComment = postCommentPort.findById(commentId) ?: throw CustomException(message = "존재하지 않는 댓글입니다.")

        postCommentReportPort.findByPostCommentIdAndUserId(postCommentId = commentId, userId = loginUser.id)
            ?.let {
                postCommentReportPort.deleteById(it.id)
                val removedReportPostComment = postComment.removeReport()
                postCommentPort.save(removedReportPostComment)
            }
            ?: run {
                if (request == null) {
                    throw CustomException(message = "신고 사유를 선택해주세요.")
                }

                val postCommentReport =
                    PostCommentReport(postCommentId = commentId, userId = loginUser.id)
                val postCommentReportReasons = request.reportReasonRequests
                    .map {
                        PostCommentReportReason(
                            reportReasonWithCustomReason = ReportReasonWithCustomReason(
                                reportReason = reportReasonPort.findById(it.reportReasonId) ?: throw CustomException(
                                    message = "존재하지 않는 신고 사유입니다."
                                ),
                                customReason = it.customReason
                            )
                        )
                    }
                postCommentReport.addReportReasons(postCommentReportReason = postCommentReportReasons)
                postCommentReportPort.save(postCommentReport)

                val addedReportPostComment = postComment.addReport()
                postCommentPort.save(addedReportPostComment)
            }
    }

}
