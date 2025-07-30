package com.wespot.post

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class PostImage(
    val id: Long,
    val postId: Long,
    val url: String,
    val createdAt: LocalDateTime
) {

    init {

        require(url.startsWith("http")) {
            throw CustomException(
                status = HttpStatus.BAD_REQUEST,
                view = ExceptionView.TOAST,
                message = "URL은 무조건 Http로 시작해야합니다."
            )
        }

    }

    companion object {

        fun of(postId: Long = 0L, cloudFrontUrl: String, imageUrl: String): PostImage {
            return PostImage(
                id = 0L,
                postId = postId,
                url = "${cloudFrontUrl}/${imageUrl}",
                createdAt = LocalDateTime.now()
            )
        }

    }

    fun addedPost(postId: Long): PostImage {
        return update(
            postId = postId
        )
    }

    private fun update(
        id: Long = this.id,
        postId: Long = this.postId,
        url: String = this.url,
        createdAt: LocalDateTime = this.createdAt
    ): PostImage {
        return PostImage(
            id = id,
            postId = postId,
            url = url,
            createdAt = createdAt
        )
    }

}
