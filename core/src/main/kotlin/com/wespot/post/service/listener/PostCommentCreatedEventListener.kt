package com.wespot.post.service.listener

import com.wespot.comment.event.PostCommentCreatedEvent
import com.wespot.exception.CustomException
import com.wespot.post.port.out.PostPort
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class PostCommentCreatedEventListener(
    private val postPort: PostPort
) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    private fun listenPostCreatedEvent(postCommentCreatedEvent: PostCommentCreatedEvent) {
        val postComment = postCommentCreatedEvent.postComment
        val post = postPort.findById(postId = postComment.postId)
            ?: throw CustomException(message = "존재하지 않는 게시글입니다.")

        post.addComment()
        postPort.save(post)
    }

}
