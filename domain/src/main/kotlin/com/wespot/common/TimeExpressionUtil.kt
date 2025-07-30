package com.wespot.common

import java.time.Duration
import java.time.LocalDateTime

object TimeExpressionUtil {

    fun postTime(now: LocalDateTime = LocalDateTime.now(), createdAt: LocalDateTime): String {
        return timeToString(createdAt, now)
    }

    private fun timeToString(createdAt: LocalDateTime, now: LocalDateTime): String {
        if (createdAt.year != now.year) {
            return "${createdAt.year % 100}.${createdAt.monthValue}.${createdAt.dayOfMonth} ${createdAt.hour}:${createdAt.minute}"
        }

        val duration = Duration.between(createdAt, now)
        return when {
            duration.toMinutes() <= 10 -> "방금"
            duration.toDays() < 1 -> "${duration.toHours()}시간 전"
            duration.toDays() < 30 -> "${duration.toDays()}일 전"
            else -> "${createdAt.monthValue}.${createdAt.dayOfMonth} ${createdAt.hour}:${createdAt.minute}"
        }
    }

    fun commentTime(now: LocalDateTime = LocalDateTime.now(), createdAt: LocalDateTime): String {
        return timeToString(createdAt, now)
    }

}
