package com.wespot.post.dto.response

import com.wespot.post.PostImage
import java.time.LocalDateTime

data class PostImageResponse(
    val id: Long,
    val postId: Long,
    val url: String,
    val createdAt: LocalDateTime
) {

    companion object {

        fun from(postImage: PostImage): PostImageResponse {
            return PostImageResponse(
                id = postImage.id,
                postId = postImage.postId,
                url = postImage.url,
                createdAt = postImage.createdAt
            )
        }

    }

}
