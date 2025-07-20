package com.wespot.view

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import org.springframework.http.HttpStatus
import java.util.*

enum class OnBoardingCategory {

    MESSAGE,
    VOTE,
    ANSWER_MESSAGE,
    POST,
    ;

    companion object {

        fun from(category: String): OnBoardingCategory {
            val findValue = valueOf(category.uppercase())

            if (Objects.isNull(findValue)) {
                throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "존재하지 않는 온보딩 바텀 sheet 타입입니다.")
            }

            return findValue
        }

    }

}
