package com.wespot.post.vo

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import org.springframework.http.HttpStatus

data class PostTitle(
    val content: String
) {

    companion object {
        private const val TITLE_LENGTH_UPPER_BOUND_INCLUSIVE = 40
    }

    init {

        require(content.isNotBlank() && content.length <= TITLE_LENGTH_UPPER_BOUND_INCLUSIVE) {
            throw CustomException(
                status = HttpStatus.BAD_REQUEST,
                view = ExceptionView.TOAST,
                message = "글의 제목은 1 ~ $TITLE_LENGTH_UPPER_BOUND_INCLUSIVE 글자여야합니다."
            )
        }
    }

}
