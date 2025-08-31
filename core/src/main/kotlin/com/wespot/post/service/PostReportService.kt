package com.wespot.post.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.exception.CustomException
import com.wespot.post.PostReport
import com.wespot.post.PostReportReason
import com.wespot.post.dto.request.PostReportRequest
import com.wespot.post.port.`in`.PostReportUseCase
import com.wespot.post.port.out.PostPort
import com.wespot.post.port.out.PostReportPort
import com.wespot.report.ReportReasonWithCustomReason
import com.wespot.report.port.out.ReportReasonPort
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostReportService(
    private val userPort: UserPort,
    private val postPort: PostPort,
    private val postReportPort: PostReportPort,
    private val reportReasonPort: ReportReasonPort,
) : PostReportUseCase {

    @Transactional
    override fun reportPost(postId: Long, postReportRequest: PostReportRequest?) {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)

        val post = postPort.findById(postId)
            ?: throw CustomException(message = "존재하지 않는 게시글입니다.")

        postReportPort.findByPostIdAndUserId(postId, loginUser.id)
            ?.let {
                postReportPort.deleteById(id = it.id)
            }
            ?: run {
                if (postReportRequest == null) {
                    throw CustomException(message = "신고 사유를 선택해주세요.")
                }

                val postReport = PostReport(
                    postId = post.id,
                    userId = loginUser.id,
                )

                val postReportReasons = postReportRequest.reportReasonRequests
                    .map {
                        PostReportReason(
                            reportReasonWithCustomReason = ReportReasonWithCustomReason(
                                reportReason = reportReasonPort.findById(it.reportReasonId)
                                    ?: throw CustomException(message = "존재하지 않는 신고 사유입니다."),
                                customReason = it.customReason
                            )
                        )
                    }

                postReport.addReportReasons(postReportReasons)
                postReportPort.save(postReport)
            }
    }

}
