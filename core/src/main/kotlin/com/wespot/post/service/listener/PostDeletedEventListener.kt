package com.wespot.post.service.listener

import com.wespot.comment.port.out.PostCommentLikePort
import com.wespot.comment.port.out.PostCommentPort
import com.wespot.comment.port.out.PostCommentReportPort
import com.wespot.post.event.PostDeletedEvent
import com.wespot.post.port.out.*
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class PostDeletedEventListener(
    private val postCommentPort: PostCommentPort,
    private val postCommentLikePort: PostCommentLikePort,
    private val postCommentReportPort: PostCommentReportPort,
    private val postNotificationPort: PostNotificationPort,
    private val postBlockPort: PostBlockPort,
    private val postReportPort: PostReportPort,
    private val postLikePort: PostLikePort,
    private val postScrapPort: PostScrapPort,
    private val postImagePort: PostImagePort,
) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun listenPostDeletedEvent(postDeletedEvent: PostDeletedEvent) {
        val postId = postDeletedEvent.deletedPost.id
        val toDeletePostCommentIds = postCommentPort.findAllByPostId(postId)
            .map { it.id }

        postCommentPort.deleteByPostId(postId)
        postCommentLikePort.deleteByPostCommentIdIn(toDeletePostCommentIds)
        postCommentReportPort.deleteByPostCommentIdIn(toDeletePostCommentIds)
        postNotificationPort.deleteByPostId(postId)
        postBlockPort.deleteByPostId(postId)
        postReportPort.deleteByPostId(postId)
        postLikePort.deleteByPostId(postId)
        postScrapPort.deleteByPostId(postId)
        postImagePort.deleteByPostId(postId)
    }
}
