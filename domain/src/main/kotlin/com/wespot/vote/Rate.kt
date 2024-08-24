package com.wespot.vote

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import org.springframework.http.HttpStatus

data class Rate(
    val value: Int
) {

    companion object {

        fun from(value: Int): Rate {
            validate(value)
            return Rate(value)
        }

        private fun validate(value: Int) {
            if (0 < value) {
                return
            }
            throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "등수는 0 이하일 수 없습니다.")
        }

        fun createMeaningLessRate(): Rate {
            return Rate(Int.MAX_VALUE)
        }

    }

}
