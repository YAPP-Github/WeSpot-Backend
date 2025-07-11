package com.wespot.post.service.listener

import com.wespot.post.PostNotification
import com.wespot.post.event.PostCreatedEvent
import com.wespot.post.port.out.PostNotificationPort
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class PostNotificationEventListener(
    private val postNotificationPort: PostNotificationPort
) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    private fun listenPostCreatedEvent(postCreatedEvent: PostCreatedEvent) {
        val post = postCreatedEvent.post

        val postNotifications = postNotificationPort.findAllByPostId(postId = post.id)
        postNotifications.find { it.user.id == post.user.id } ?: postNotificationPort.save(
            PostNotification.of(
                user = post.user, postId = post.id
            )
        )
    }

}
