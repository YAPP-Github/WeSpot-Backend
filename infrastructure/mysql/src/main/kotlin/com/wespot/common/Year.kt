package com.wespot.common

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import org.springframework.http.HttpStatus
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


data class Year(
    val year: Int
) {

    companion object {

        fun from(year: Int): Year {
            if (year <= 0) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST,
                    ExceptionView.TOAST,
                    "연도는 음수일 수 없습니다."
                )
            }
            return Year(year)
        }

    }

    fun startDateAtYear(): LocalDate {
        return LocalDate.of(year, 1, 1)
    }

    fun endDateAtYear(): LocalDate {
        return LocalDate.of(year, 12, 31)
    }

    fun startDateTimeAtYear(): LocalDateTime {
        return LocalDate.of(year, 1, 1).atStartOfDay()
    }

    fun endDateTimeAtYear(): LocalDateTime {
        return LocalDateTime.of(LocalDate.of(year, 12, 31), LocalTime.MAX)
    }
}
