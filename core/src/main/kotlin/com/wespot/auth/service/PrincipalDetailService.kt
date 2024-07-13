package com.wespot.auth.service

import com.wespot.auth.PrincipalDetails
import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.NoSuchElementException

@Service
class PrincipalDetailService(
    private val userPort: UserPort
) : UserDetailsService {
    override fun loadUserByUsername(userEmail: String): UserDetails {
        val principal: User = userPort.getByEmail(userEmail) ?: throw NoSuchElementException("유저를 찾을 수 없습니다.")
        return PrincipalDetails(principal)
    }
}