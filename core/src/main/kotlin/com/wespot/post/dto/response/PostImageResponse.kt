package com.wespot.post.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.post.PostImage
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PostImageResponse(
    val id: Long,
    val postId: Long,
    val url: String,
    val width: Int,
    val height: Int,
    val createdAt: LocalDateTime
) {

    companion object {

        fun from(postImage: PostImage): PostImageResponse {
            return PostImageResponse(
                id = postImage.id,
                postId = postImage.postId,
                url = postImage.url,
                width = postImage.width,
                height = postImage.height,
                createdAt = postImage.createdAt
            )
        }

    }

}
