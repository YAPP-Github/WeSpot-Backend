package com.wespot.post.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.comment.PostComment
import com.wespot.common.TimeExpressionUtil
import com.wespot.post.PostProfile

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PostCommentResponse(
    val isMe: Boolean,
    val authorImage: String,
    val authorName: String,
    val content: String,
    val likeCount: Long,
    val hasPushedLike: Boolean,
    val isReported: Boolean,
    val createdAt: String,
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
                isMe = isPostOwner,
                authorName = if (isPostOwner) OWNER_NAME else VIEWER_NAME,
                content = postComment.content.content,
                likeCount = postComment.likeCount,
                hasPushedLike = postComment.postCommentStatusByViewer?.isViewerPushedLike ?: false,
                isReported = postComment.reportCount > 0,
                createdAt = TimeExpressionUtil.commentTime(createdAt = postComment.createdAt)
            )
        }

    }

}
