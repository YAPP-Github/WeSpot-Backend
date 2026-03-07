package com.wespot.admin.service

import com.wespot.admin.dto.AdminUserResponse
import com.wespot.admin.port.`in`.AdminUserUseCase
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AdminUserService(
    private val userPort: UserPort,
) : AdminUserUseCase {

    override fun getAllUsers(): List<AdminUserResponse> {
        return userPort.findAll().map { AdminUserResponse.from(it) }
    }

}
