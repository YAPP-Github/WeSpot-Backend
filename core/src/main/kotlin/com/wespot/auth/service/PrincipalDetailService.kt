package com.wespot.auth.service

import com.wespot.auth.PrincipalDetails
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class PrincipalDetailService(
    private val userPort: UserPort
) : UserDetailsService {
    override fun loadUserByUsername(userEmail: String): UserDetails {
        val principal: User = userPort.findByEmail(userEmail) ?: throw CustomException(
            HttpStatus.NOT_FOUND,
            ExceptionView.TOAST,
            "유저를 찾을 수 없습니다."
        )

        return PrincipalDetails(principal)
    }
}
