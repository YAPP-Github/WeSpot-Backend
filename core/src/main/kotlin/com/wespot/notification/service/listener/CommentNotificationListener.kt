package com.wespot.notification.service.listener

import com.wespot.comment.event.PostCommentCreatedEvent
import com.wespot.common.NotificationUtil
import com.wespot.exception.CustomException
import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import com.wespot.notification.service.NotificationHelper
import com.wespot.post.port.out.PostNotificationPort
import com.wespot.post.port.out.PostPort
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
    private val postNotificationPort: PostNotificationPort,
    private val notificationHelper: NotificationHelper,
) {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun listenCreatedPostCommentEvent(postCommentCreatedEvent: PostCommentCreatedEvent) { // TODO : 일단, 본인은 알림 못받게 해야하고, 익명 프로필로
        val postComment = postCommentCreatedEvent.postComment
        val post = postPort.findById(postComment.postId) ?: throw CustomException(
            status = HttpStatus.BAD_REQUEST,
            message = "존재하지 않는 게시글입니다."
        )
        val commentRegister = postComment.user
        val postNotifications = postNotificationPort.findAllByPostId(post.id)

        val users = listOf(post.user) + postNotifications.map { it.user }.distinct()
        val notifications = users.stream()
            .map {
                Notification.create(
                    userId = it.id,
                    type = NotificationType.COMMENT,
                    targetId = post.id,
                    title = "${commentRegister.name} 님이 댓글을 남겼습니다.",
                    body = NotificationUtil.summaryContent(
                        content = postComment.content.content
                    ),
                )
            }
        val usersGroup = users.associateBy { it.id }
        notifications.forEach { notificationHelper.sendNotification(usersGroup[it.userId], it) }
    }
}
