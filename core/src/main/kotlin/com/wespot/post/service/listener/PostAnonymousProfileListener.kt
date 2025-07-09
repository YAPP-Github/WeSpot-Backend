package com.wespot.post.service.listener

import com.wespot.comment.event.PostCommentCreatedEvent
import com.wespot.post.PostProfile
import com.wespot.post.event.PostCreatedEvent
import com.wespot.post.port.out.PostProfilePort
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class PostAnonymousProfileListener(
    private val postProfilePort: PostProfilePort
) {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun listenCreatedPostEvent(postCreatedEvent: PostCreatedEvent) {
        val post = postCreatedEvent.post
        val userId = post.user.id
        postProfilePort.findByUserId(userId = userId) ?: postProfilePort.save(PostProfile.from(userId = userId))
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun listenCreatedPostCommentEvent(postCommentCreatedEvent: PostCommentCreatedEvent) {
        val postComment = postCommentCreatedEvent.postComment
        val userId = postComment.user.id
        postProfilePort.findByUserId(userId = userId) ?: postProfilePort.save(PostProfile.from(userId = userId))
    }

}
