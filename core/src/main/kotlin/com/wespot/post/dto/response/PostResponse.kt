package com.wespot.post.dto.response

import com.wespot.post.Post
import com.wespot.user.dto.response.UserResponse
import java.time.LocalDateTime

data class PostResponse(
    val id: Long = 0L,
    val category: PostCategoryDetailResponses.PostCategoryDetailResponse,
    val user: UserResponse,
    val title: String? = null,
    val description: String,
    val likeCount: Long = 0,
    val isViewerPushedLike: Boolean = false,
    val commentCount: Long = 0,
    val isViewerPushedScrap: Boolean = false,
    val isViewerPushedNotification: Boolean = false,
    val images: List<PostImageResponse>? = null,
    val createdAt: LocalDateTime,
) {

    companion object {
        fun from(post: Post): PostResponse {
            val user = post.user
            return PostResponse(
                id = post.id,
                category = PostCategoryDetailResponses.PostCategoryDetailResponse.from(postCategory = post.category),
                user = UserResponse.from(user = user, school = user.school.name),
                title = post.title?.content,
                description = post.description.content,
                likeCount = post.likeCount,
                commentCount = post.commentCount,
                images = post.images?.postImages?.map { PostImageResponse.from(it) },
                isViewerPushedLike = post.postStatusByViewer?.isViewerPushedLike ?: false,
                isViewerPushedScrap = post.postStatusByViewer?.isViewerPushedScrap ?: false,
                isViewerPushedNotification = post.postStatusByViewer?.isViewerPushedNotification ?: false,
                createdAt = post.createdAt
            )
        }
    }

}
