package com.wespot.post

import com.wespot.EventUtils
import com.wespot.exception.CustomException
import com.wespot.post.event.PostCreatedEvent
import com.wespot.post.vo.PostDescription
import com.wespot.post.vo.PostTitle
import com.wespot.user.User
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class Post(
    val id: Long = 0L,
    val category: PostCategory,
    val user: User,
    val title: PostTitle? = null,
    val description: PostDescription,
    val likeCount: Long = 0,
    val commentCount: Long = 0,
    val bookmarkedCount: Long = 0,
    val images: PostImages? = null,
    val profile: PostProfile = PostProfile.DEFAULT_PROFILE,

    val postStatusByViewer: PostStatusByViewer? = null,

    val createdAt: LocalDateTime = LocalDateTime.now(),
) {

    companion object {

        fun of(
            category: PostCategory,
            user: User,
            title: String? = null,
            description: String,
            images: List<PostImage>? = listOf(),
            toSavePost: (Post) -> Post
        ): Post {
            val post = Post(
                category = category,
                user = user,
                title = title?.let { PostTitle(content = it) },
                description = PostDescription(content = description),
            )
            val savedPost = toSavePost.invoke(post)
            val postImages = images?.let { PostImages.of(postId = savedPost.id, postImages = images) }

            val imagesAddedPost = savedPost.addImages(images = postImages)
            val completedPost = toSavePost.invoke(imagesAddedPost)

            EventUtils.publish(PostCreatedEvent(post = completedPost))
            return completedPost
        }

    }

    private fun addImages(images: PostImages?): Post {
        return copyAndUpdateField(images = images)
    }

    private fun copyAndUpdateField(
        id: Long = this.id,
        category: PostCategory = this.category,
        user: User = this.user,
        title: PostTitle? = this.title,
        description: PostDescription = this.description,
        likeCount: Long = this.likeCount,
        commentCount: Long = this.commentCount,
        bookmarkedCount: Long = this.bookmarkedCount,
        images: PostImages? = this.images,
        profile: PostProfile = this.profile,
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
            bookmarkedCount = bookmarkedCount,
            images = images,
            createdAt = createdAt,
            profile = profile,
        )
    }

    fun isAuthor(userId: Long): Boolean {
        return user.id == userId
    }

    fun update(
        category: PostCategory,
        user: User,
        title: String? = null,
        description: String,
        images: List<PostImage>? = listOf(),
        toUpdatePost: (Post) -> Post
    ): Post {
        require(isAuthor(user.id)) {
            throw CustomException(
                status = HttpStatus.FORBIDDEN, message = "게시글 작성자가 아닙니다.",
            )
        }

        val updatedPost = copyAndUpdateField(
            category = category,
            title = title?.let { PostTitle(content = it) },
            description = PostDescription(content = description),
            images = images?.let { PostImages.of(postId = id, postImages = images) },
        )

        return toUpdatePost(updatedPost)
    }

    fun addLike(): Post {
        return copyAndUpdateField(likeCount = this.likeCount + 1)
    }

    fun removeLike(): Post {
        return copyAndUpdateField(likeCount = this.likeCount - 1)
    }

    fun addComment(): Post {
        return copyAndUpdateField(commentCount = this.commentCount + 1)
    }

    fun scoreOfPost(): Long {
        return likeCount * 3 + commentCount * 2 + bookmarkedCount
    }

    fun deleteBookmark(): Post {
        return copyAndUpdateField(bookmarkedCount = this.bookmarkedCount - 1)
    }

    fun addBookmark(): Post {
        return copyAndUpdateField(bookmarkedCount = this.bookmarkedCount + 1)
    }

}
