package com.wespot.comment.service.listener

import com.wespot.comment.event.PostCommentDeleteEvent
import com.wespot.comment.port.out.PostCommentLikePort
import com.wespot.comment.port.out.PostCommentReportPort
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class PostCommentDeleteEventListener(
    private val postCommentLikePort: PostCommentLikePort,
    private val postCommentReportPort: PostCommentReportPort
) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    private fun listenPostCommentDeleteEvent(postCommentDeleteEvent: PostCommentDeleteEvent) {
        val commentId = listOf(postCommentDeleteEvent.postComment.id)
        postCommentLikePort.deleteByPostCommentIdIn(commentId)
        postCommentReportPort.deleteByPostCommentIdIn(commentId)
    }

}
