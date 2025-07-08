package com.wespot.post

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import org.springframework.http.HttpStatus

data class PostImages(
    val postImages: List<PostImage>
) {

    companion object {

        private const val IMAGE_MAX_COUNT_INCLUSIVE = 3

        fun of(postId: Long, cloudFrontUrl: String, images: List<String>): PostImages {
            val values = images.map { PostImage.of(postId = postId, cloudFrontUrl = cloudFrontUrl, imageUrl = it) }

            return PostImages(values)
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
