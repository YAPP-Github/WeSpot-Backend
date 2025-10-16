package com.wespot.notification.service.listener

import com.wespot.comment.event.PostCommentCreatedEvent
import com.wespot.common.NotificationUtil
import com.wespot.exception.CustomException
import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import com.wespot.notification.port.out.NotificationPort
import com.wespot.notification.service.NotificationHelper
import com.wespot.post.port.out.PostNotificationPort
import com.wespot.post.port.out.PostPort
import com.wespot.post.port.out.PostProfilePort
import org.springframework.http.HttpStatus
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class CommentNotificationListener(
    private val postPort: PostPort,
    private val postProfilePort: PostProfilePort,
    private val postNotificationPort: PostNotificationPort,
    private val notificationHelper: NotificationHelper,
    private val notificationPort: NotificationPort,
) {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun listenCreatedPostCommentEvent(postCommentCreatedEvent: PostCommentCreatedEvent) {
        val postComment = postCommentCreatedEvent.postComment
        val post = postPort.findById(postComment.postId) ?: throw CustomException(
            status = HttpStatus.BAD_REQUEST,
            message = "존재하지 않는 게시글입니다."
        )
        val commentRegister = postComment.user
        val postNotifications = postNotificationPort.findAllByPostId(post.id)

        val users = postNotifications.map { it.user }.distinct()
        val notifications = users.stream()
            .filter { postComment.isNotAuthor(it.id) }
            .map {
                Notification.create(
                    userId = it.id,
                    type = NotificationType.POST_COMMENT,
                    targetId = post.id,
                    title = "${post.commentProfileName(commentRegister)} 님이 댓글을 남겼습니다.",
                    body = NotificationUtil.summaryContent(
                        content = postComment.content.content
                    ),
                )
            }
            .toList()
        notificationPort.saveAll(notifications)
        val usersGroup = users.associateBy { it.id }
        notifications.forEach { notificationHelper.sendNotification(usersGroup[it.userId], it) }
    }
}
