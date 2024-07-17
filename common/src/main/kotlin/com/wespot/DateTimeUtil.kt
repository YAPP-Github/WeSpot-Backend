package com.wespot

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object DateTimeUtil {
    fun getExpirationLocalDateTime(expireTimeInMillis: Long): LocalDateTime {
        val now = Instant.now()
        val expirationInstant = now.plusMillis(expireTimeInMillis)
        return LocalDateTime.ofInstant(expirationInstant, ZoneId.systemDefault())
    }
}
