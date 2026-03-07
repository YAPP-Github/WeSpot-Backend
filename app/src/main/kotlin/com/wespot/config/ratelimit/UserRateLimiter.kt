package com.wespot.config.ratelimit

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class UserRateLimiter {

    private val lastRequestTime = ConcurrentHashMap<String, Long>()

    fun tryConsume(userKey: String, endpoint: String): Boolean {
        val key = "$userKey:$endpoint"
        val now = System.currentTimeMillis()
        var allowed = false

        lastRequestTime.compute(key) { _, last ->
            if (last == null || now - last >= 2000L) {
                allowed = true
                now
            } else {
                last
            }
        }

        return allowed
    }
}
