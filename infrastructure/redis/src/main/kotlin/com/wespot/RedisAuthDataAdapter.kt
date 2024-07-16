package com.wespot


import com.wespot.auth.dto.AuthData
import com.wespot.auth.port.out.AuthDataPort
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedisAuthDataAdapter(
    private val redisTemplate: RedisTemplate<String, Any>
) : AuthDataPort {

    override fun saveAuthData(token: String, authData: AuthData) {
        redisTemplate.opsForValue().set(token, authData, 25, TimeUnit.MINUTES)
    }

    override fun getAuthData(token: String): AuthData? {
        return redisTemplate.opsForValue().get(token) as? AuthData
    }
}