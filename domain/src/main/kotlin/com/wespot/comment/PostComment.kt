package com.wespot.comment

import com.wespot.comment.vo.PostCommentContent
import com.wespot.user.User
import java.time.LocalDateTime

class PostComment(
    val id: Long = 0L,
    val postId: Long,
    val user: User,
    val content: PostCommentContent,
    val likeCount: Long = 0L,
    val reportCount: Long = 0L,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {

    companion object {
        fun of(
            postId: Long,
            user: User,
            content: String,
        ): PostComment {
            return PostComment(
                postId = postId,
                user = user,
                content = PostCommentContent(content = content)
            )
        }
    }

}
