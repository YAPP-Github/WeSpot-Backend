package com.wespot.post.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.post.PostReport
import com.wespot.post.dto.request.PostReportRequest
import com.wespot.post.port.`in`.PostReportUseCase
import com.wespot.post.port.out.PostPort
import com.wespot.post.port.out.PostReportPort
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostReportService(
    private val userPort: UserPort,
    private val postPort: PostPort,
    private val postReportPort: PostReportPort,
) : PostReportUseCase {

    @Transactional
    override fun reportPost(postId: Long, postReportRequest: PostReportRequest) {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)

        val post = postPort.findById(postId)
            ?: throw IllegalArgumentException("존재하지 않는 게시글입니다.")

        postReportPort.findByPostIdAndUserId(postId, loginUser.id)
            ?.let {
                postReportPort.deleteById(id = it.id)
            }
            ?: {
                val postReport = PostReport(postId = post.id, userId = loginUser.id, reason = postReportRequest.reason)
                postReportPort.save(postReport)
            }
    }

}
