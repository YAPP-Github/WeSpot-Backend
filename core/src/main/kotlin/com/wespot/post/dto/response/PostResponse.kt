package com.wespot.post.dto.response

import com.wespot.post.Post
import com.wespot.user.dto.response.UserResponse
import java.time.LocalDateTime

data class PostResponse(
    val id: Long = 0L,
    val category: PostCategoryDetailResponses.PostCategoryDetailResponse,
    val user: UserResponse,
    val title: String?,
    val description: String,
    val likeCount: Int,
    val commentCount: Int,
    val images: List<PostImageResponse>?,
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
                createdAt = post.createdAt
            )
        }
    }

}
