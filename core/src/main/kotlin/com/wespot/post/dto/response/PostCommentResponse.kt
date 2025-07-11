package com.wespot.post.dto.response

import com.wespot.comment.PostComment
import com.wespot.post.PostProfile
import java.time.LocalDateTime

data class PostCommentResponse(
    val authorImage: String,
    val authorName: String,
    val content: String,
    val likeCount: Long,
    val didIPushLike: Boolean,
    val isReported: Boolean,
    val createdAt: LocalDateTime,
) {

    companion object {
        private const val OWNER_NAME = "익명의 글쓴이"
        private const val VIEWER_NAME = "익명의 댓쓴이"

        fun of(
            isPostOwner: Boolean = false,
            postComment: PostComment,
            postProfile: PostProfile
        ): PostCommentResponse {
            return PostCommentResponse(
                authorImage = postProfile.url,
                authorName = if (isPostOwner) OWNER_NAME else VIEWER_NAME,
                content = postComment.content.content,
                likeCount = postComment.likeCount,
                didIPushLike = postComment.postCommentStatusByViewer?.isViewerPushedLike ?: false,
                isReported = postComment.reportCount > 0,
                createdAt = postComment.createdAt
            )
        }

    }

}
