package com.wespot.common

import java.time.Duration
import java.time.LocalDateTime

object TimeExpressionUtil {

    fun detailTime(now: LocalDateTime = LocalDateTime.now(), createdAt: LocalDateTime): String {
        return timeToString(createdAt = createdAt, now = now, isDetail = true)
    }

    private fun timeToString(createdAt: LocalDateTime, now: LocalDateTime, isDetail: Boolean = false): String {
        val duration = Duration.between(createdAt, now)
        val yearString = fillWithZero(createdAt.year % 100)
        val monthString = fillWithZero(createdAt.monthValue)
        val dayString = fillWithZero(createdAt.dayOfMonth)
        val hourString = fillWithZero(createdAt.hour)
        val minuteString = fillWithZero(createdAt.minute)

        return when {
            duration.toMinutes() < 1 -> "방금"
            isDetail && duration.toMinutes() < 60 -> "${duration.toMinutes()}분 전"
            isDetail && now.dayOfMonth == createdAt.dayOfMonth && duration.toHours() < 24 -> "${hourString}:${minuteString}"
            isDetail && now.year == createdAt.year && duration.toDays() < 365 -> "${monthString}/${dayString}"
            isDetail && 365 <= duration.toDays() -> "${if ((duration.toDays() / 365).toInt() == 0) 1 else duration.toDays() / 365}년 전"
            now.year == createdAt.year && duration.toDays() < 365 -> "${monthString}/${dayString} ${hourString}:${minuteString}"
            else -> "${yearString}/${monthString}/${dayString} ${hourString}:${minuteString}"
        }
    }

    private fun fillWithZero(value: Int): String {
        if (value < 10) {
            return "0$value"
        }

        return value.toString()
    }

    fun isNotDetailTime(now: LocalDateTime = LocalDateTime.now(), createdAt: LocalDateTime): String {
        return timeToString(createdAt = createdAt, now = now)
    }

}
