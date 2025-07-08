package com.wespot.post.vo

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import org.springframework.http.HttpStatus

data class PostDescription(
    val content: String
) {

    companion object {
        private const val DESCRIPTION_LENGTH_UPPER_BOUND_INCLUSIVE = 1200
    }

    init {

        require(content.isNotBlank() && content.length <= DESCRIPTION_LENGTH_UPPER_BOUND_INCLUSIVE) {
            throw CustomException(
                status = HttpStatus.BAD_REQUEST,
                view = ExceptionView.TOAST,
                message = "글의 본문은 1 ~ $DESCRIPTION_LENGTH_UPPER_BOUND_INCLUSIVE 자여야 합니다."
            )
        }
    }

}

