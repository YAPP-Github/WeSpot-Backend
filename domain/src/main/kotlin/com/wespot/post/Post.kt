package com.wespot.post

import com.wespot.EventUtils
import com.wespot.post.event.PostCreatedEvent
import com.wespot.post.vo.PostDescription
import com.wespot.post.vo.PostTitle
import com.wespot.user.User
import java.time.LocalDateTime

class Post(
    val id: Long = 0L,
    val category: PostCategory,
    val user: User,
    val title: PostTitle? = null,
    val description: PostDescription,
    val likeCount: Int = 0,
    val commentCount: Int = 0,
    val images: PostImages? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {

    companion object {

        fun of(
            category: PostCategory,
            user: User,
            title: String? = null,
            description: String,
            images: List<String> = listOf(),
            cloudFrontUrl: String,
            toSavePost: (Post) -> Post
        ): Post {
            val post = Post(
                category = category,
                user = user,
                title = title?.let { PostTitle(content = it) },
                description = PostDescription(content = description),
            )
            val savedPost = toSavePost.invoke(post)
            val postImages = PostImages.of(postId = savedPost.id, cloudFrontUrl = cloudFrontUrl, images = images)

            val imagesAddedPost = savedPost.addImages(images = postImages)
            val completedPost = toSavePost.invoke(imagesAddedPost)

            EventUtils.publish(PostCreatedEvent(post = completedPost))
            return completedPost
        }

    }

    private fun addImages(images: PostImages): Post {
        return copy(images = images)
    }

    private fun copy(
        id: Long = this.id,
        category: PostCategory = this.category,
        user: User = this.user,
        title: PostTitle? = this.title,
        description: PostDescription = this.description,
        likeCount: Int = this.likeCount,
        commentCount: Int = this.commentCount,
        images: PostImages? = this.images,
        createdAt: LocalDateTime = this.createdAt
    ): Post {
        return Post(
            id = id,
            user = user,
            category = category,
            title = title,
            description = description,
            likeCount = likeCount,
            commentCount = commentCount,
            images = images,
            createdAt = createdAt
        )
    }

}
