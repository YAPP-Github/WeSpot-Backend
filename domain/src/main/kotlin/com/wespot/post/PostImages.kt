package com.wespot.post

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import org.springframework.http.HttpStatus

data class PostImages(
    val postImages: List<PostImage>
) {

    fun isThereOnlyNewImages(): Boolean {
        return postImages.all { it.isNew() }
    }

    companion object {

        private const val IMAGE_MAX_COUNT_INCLUSIVE = 3

        fun of(postId: Long, postImages: List<PostImage>): PostImages {
            return PostImages(
                postImages.map { it.addedPost(postId) }
            )
        }

    }

    init {

        require(postImages.size <= IMAGE_MAX_COUNT_INCLUSIVE) {
            throw CustomException(
                status = HttpStatus.BAD_REQUEST,
                view = ExceptionView.TOAST,
                message = "이미지는 최대 ${IMAGE_MAX_COUNT_INCLUSIVE}개를 넘을 수 없습니다."
            )
        }

    }

}
