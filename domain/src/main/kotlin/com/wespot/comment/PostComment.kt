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

    val postCommentStatusByViewer: PostCommentStatusByViewer? = null,

    val createdAt: LocalDateTime = LocalDateTime.now(),
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
                content = PostCommentContent.from(content = content)
            )
        }
    }

    fun isWriter(userId: Long): Boolean {
        return user.id == userId
    }

    fun removeLike(): PostComment {
        return update(
            likeCount = this.likeCount - 1
        )
    }

    private fun update(
        id: Long = this.id,
        postId: Long = this.postId,
        user: User = this.user,
        content: PostCommentContent = this.content,
        likeCount: Long = this.likeCount,
        reportCount: Long = this.reportCount,
        postCommentStatusByViewer: PostCommentStatusByViewer? = this.postCommentStatusByViewer,
        createdAt: LocalDateTime = this.createdAt,
    ): PostComment {
        return PostComment(
            id = id,
            postId = postId,
            user = user,
            content = content,
            likeCount = likeCount,
            reportCount = reportCount,
            postCommentStatusByViewer = postCommentStatusByViewer,
            createdAt = createdAt
        )
    }

    fun addLike(): PostComment {
        return update(
            likeCount = this.likeCount + 1
        )
    }

    fun removeReport(): PostComment {
        return update(
            reportCount = this.reportCount - 1
        )
    }

    fun addReport(): PostComment {
        return update(
            reportCount = this.reportCount + 1
        )
    }

    fun isAuthor(id: Long): Boolean {
        return user.id == id
    }

}
