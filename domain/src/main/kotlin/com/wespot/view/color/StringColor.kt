package com.wespot.view.color

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import org.springframework.http.HttpStatus

data class StringColor(
    val value: String
) {

    companion object {
        fun from(value: String): StringColor {
            validate(value)
            return StringColor(value)
        }

        private fun validate(color: String) {
            if (color.length != 9 || color[0] != '#') {
                throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "color length must be 9")
            }

            val containsInvalidLetter = color.substring(1)
                .chars()
                .anyMatch { !(Character.isUpperCase(it) || Character.isDigit(it)) }

            if (containsInvalidLetter) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST,
                    ExceptionView.TOAST,
                    "color must be uppercase"
                )
            }
        }
    }

}
