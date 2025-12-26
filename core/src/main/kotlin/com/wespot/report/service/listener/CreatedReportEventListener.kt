package com.wespot.report.service.listener

import com.wespot.comment.event.PostCommentBlockEvent
import com.wespot.comment.event.PostCommentReportEvent
import com.wespot.message.event.MessageV2BlockedEvent
import com.wespot.post.event.PostBlockEvent
import com.wespot.post.event.PostReportEvent
import com.wespot.report.ReportType
import com.wespot.report.port.out.ReportPort
import com.wespot.report.service.CreatedReportService
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class CreatedReportEventListener(
    val createdReportService: CreatedReportService,
) {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handleCreatedMessageBlockEvent(event: MessageV2BlockedEvent) {
        val message = event.message

        if (message.isBlockedBy(viewer = event.sender)) {
            createdReportService.saveReport(
                reportType = ReportType.MESSAGE,
                targetId = event.message.id,
                senderId = event.sender.id,
                receiverId = event.receiver.id,
                content = "메시지 차단"
            )
        }
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handleCreatedPostReportEvent(event: PostReportEvent) {
        createdReportService.saveReport(
            reportType = ReportType.COMMUNITY,
            targetId = event.post.id,
            senderId = event.sender.id,
            receiverId = event.receiver.id,
            content = event.reason
        )
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handleCreatedPostBlockEvent(event: PostBlockEvent) {
        createdReportService.saveReport(
            reportType = ReportType.COMMUNITY,
            targetId = event.post.id,
            senderId = event.sender.id,
            receiverId = event.receiver.id,
            content = "게시글 차단"
        )
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handleCreatedPostCommentReportEvent(event: PostCommentReportEvent) {
        createdReportService.saveReport(
            reportType = ReportType.COMMUNITY,
            targetId = event.postComment.id,
            senderId = event.sender.id,
            receiverId = event.receiver.id,
            content = event.reason
        )
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handleCreatedPostCommentBlockEvent(event: PostCommentBlockEvent) {
        createdReportService.saveReport(
            reportType = ReportType.COMMUNITY,
            targetId = event.postComment.id,
            senderId = event.sender.id,
            receiverId = event.receiver.id,
            content = "댓글 차단"
        )
    }

}
